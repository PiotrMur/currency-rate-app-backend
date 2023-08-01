package com.pmurawski.currencyrate.components.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmurawski.currencyrate.components.fetchingcurrencyrate.CurrencyRateService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyRateServiceTest {

    @Test
    void connectsWithNBPEndpoint() {
        CurrencyRateService currencyRateService = new CurrencyRateService(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false), (currencyCode, name, valueForCurrencyCode) -> {
        });

        Double value = currencyRateService.fetchCurrencyRateForCurrencyCode("EUR", "Kuba");

        assertThat(value).isPositive();
    }

}