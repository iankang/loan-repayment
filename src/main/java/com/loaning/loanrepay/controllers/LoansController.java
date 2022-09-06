package com.loaning.loanrepay.controllers;


import com.loaning.loanrepay.entitites.Loans;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "This manages the loans in the system")
public class LoansController {

    private LoanService loanService;

    public LoansController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Get all loans", description = "Gets all loans in the system", tags = {"Loans"})
    @GetMapping("/getAllLoans")
    public ResponseEntity<PagedResponse<Loans>> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return loanService.getAllLoans(page, size);
    }

    @Operation(summary = "Get loan by subscriber phone number", description = "Gets all loans by subscriber in the system", tags = {"Loans"})
    @GetMapping("/getAllSubscriberLoans")
    public ResponseEntity<PagedResponse<Loans>> getAllSubscriberLoans(
            @RequestParam() String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return loanService.getLoansBySubscriberId(phoneNumber,page, size);
    }
    @Operation(summary = "Request a loan", description = "Requests a loans from the system", tags = {"Loans"})
    @PostMapping("/requestLoan")
    public ResponseEntity<?> requestLoan(
            String phoneNumber,
            BigDecimal borrowAmount
    ){
        return loanService.requestLoan(phoneNumber, borrowAmount);
    }
}
