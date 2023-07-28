package com.pmurawski.currencyrate.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class CurrencyRateService {
    private static final String NBP_CURRENCY_RATES_URL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
    private final ObjectMapper objectMapper;
    private final RequestDAO requestDAO;


    public CurrencyRateService(RequestDAO requestDAO, ObjectMapper objectMapper) {
        this.requestDAO = requestDAO;
        this.objectMapper = objectMapper;
    }

    public Double fetchCurrencyRateForCurrencyCode(String currencyCode, String name) {
        currencyCode = formatCurrencyCodeToUpperCase(currencyCode);
        try {
            List<RatesCollection> listOfRateCollection = getJson(createUrl());
            double valueForCurrencyCode = getMidValueForCurrencyCode(listOfRateCollection.get(0), currencyCode);
            requestDAO.saveCurrencyValueRequest(currencyCode, name, valueForCurrencyCode);
            return valueForCurrencyCode;
        } catch (IOException e) {
            throw new CurrencyRateProcessingException("Error occurred while fetching currency rates.", e);
        }
    }

    private String formatCurrencyCodeToUpperCase(String currencyCode) {
        return currencyCode.toUpperCase();
    }

    private URL createUrl() {
        try {
            return new URL(NBP_CURRENCY_RATES_URL);
        } catch (MalformedURLException e) {
            throw new CurrencyRateRequestException("Invalid URL provided.", e);
        }
    }

    private List<RatesCollection> getJson(URL url) throws IOException {
        return objectMapper.readValue(url, new TypeReference<>() {
        });
    }

    private double getMidValueForCurrencyCode(RatesCollection ratesCollection, String code) {
        return ratesCollection.getRates().stream()
                .filter(rate -> rate.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This code [" + code + "] does not exists"))
                .getMid();

    }

    private static class CurrencyRateProcessingException extends RuntimeException {
        CurrencyRateProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class CurrencyRateRequestException extends RuntimeException {
        CurrencyRateRequestException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}