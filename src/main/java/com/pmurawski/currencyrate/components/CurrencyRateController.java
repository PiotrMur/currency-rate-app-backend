package com.pmurawski.currencyrate.components;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@CrossOrigin(origins = "http://localhost:4200/")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;
    private final RequestDAO requestDAO;

    public CurrencyRateController(CurrencyRateService currencyRateService, RequestDAO requestDAO) {
        this.currencyRateService = currencyRateService;
        this.requestDAO = requestDAO;
    }

    @PostMapping("/get-current-currency-value-command")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO requestCurrencyRate(@RequestBody RequestDTO requestDTO) {
        Double currencyRate = currencyRateService.fetchCurrencyRateForCurrencyCode(requestDTO.currency(), requestDTO.name());
        return new ResponseDTO(currencyRate);
    }

    @GetMapping("/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ValueRequestDTO> fetchAllRequests() {
        return requestDAO.fetchAllRequests();
    }
}
