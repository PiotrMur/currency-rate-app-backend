package com.pmurawski.currencyrate;

import com.pmurawski.audit.RequestDAO;
import com.pmurawski.audit.ValueRequestDTO;
import com.pmurawski.audit.ValueRequestNameOnlyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/currencies")
@CrossOrigin(origins = "http://localhost:4200/")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @PostMapping("/get-current-currency-value-command")
    @ResponseStatus(HttpStatus.CREATED)
    public CurrencyRateResponse requestCurrencyRate(@RequestBody CurrencyRateRequest currencyRateRequest) {
        Double currencyRate = currencyRateService.fetchCurrencyRateForCurrencyCode(currencyRateRequest.currency(), currencyRateRequest.name());
        return new CurrencyRateResponse(currencyRate);
    }
}
