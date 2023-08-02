package com.pmurawski.audit;

import com.pmurawski.currencyrate.CurrencyRateRequest;
import com.pmurawski.currencyrate.CurrencyRateResponse;
import com.pmurawski.currencyrate.CurrencyRateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/currencies")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuditController {

    private final RequestDAO requestDAO;

    public AuditController(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
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
    public void updateName(@RequestBody ValueRequestNameOnlyDTO nameUpdate, @PathVariable("requestId") UUID requestId) {
        requestDAO.updateName(nameUpdate, requestId);
    }

    @GetMapping("/calculate-all-requests")
    @ResponseStatus(HttpStatus.OK)
    public Integer calculateRequests() {
        return requestDAO.calculateRequests();
    }
}
