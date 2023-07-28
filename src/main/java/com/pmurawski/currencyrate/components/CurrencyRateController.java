package com.pmurawski.currencyrate.components;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/requests/{requestID}")
    @ResponseStatus(HttpStatus.OK)
    public ValueRequestDTO fetchSingleRequest(@PathVariable UUID requestID) {
        return requestDAO.fetchRequest(requestID);
    }

    @DeleteMapping("/delete-request/{requestID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSingleRequest(@PathVariable UUID requestID) {
        requestDAO.delete(requestID);
    }

    @PatchMapping("/update-request/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateName(@RequestBody ValueRequestNameOnly nameUpdate, @PathVariable("requestId") UUID requestId) {
        requestDAO.updateName(nameUpdate, requestId);
    }

    @GetMapping("/calculate-all-requests")
    @ResponseStatus(HttpStatus.OK)
    public Integer calculateRequests() {
        return requestDAO.calculateRequests();
    }
}
