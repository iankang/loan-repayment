package com.loaning.loanrepay.controllers;


import com.loaning.loanrepay.entitites.Repay;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.services.RepayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/repay")
@Tag(name = "Repay", description = "This manages the repayments in the system")
public class RepaymentController {

    private RepayService repayService;

    public RepaymentController(RepayService repayService) {
        this.repayService = repayService;
    }

    @Operation(summary = "Get all repayments", description = "Gets all repayments in the system", tags = {"Repay"})
    @GetMapping("/getAllRepayments")
    public ResponseEntity<PagedResponse<Repay>> getAllRepayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return repayService.getAllRepayments(page, size);
    }

    @Operation(summary = "Get repayment by subscriber phone number", description = "Gets all repayments by subscriber in the system", tags = {"Repay"})
    @GetMapping("/getAllSubscriberRepayments")
    public ResponseEntity<PagedResponse<Repay>> getAllSubscriberLoans(
            @RequestParam() String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return repayService.getRepaymentsBySubscriberId(phoneNumber,page, size);
    }
    @Operation(summary = "Repay a loan", description = "Repays a loans from the system", tags = {"Repay"})
    @PostMapping("/repayLoan")
    public ResponseEntity<?> requestLoan(
            String phoneNumber,
            BigDecimal repaymentAmount
    ){
        return repayService.repayLoan(phoneNumber, repaymentAmount);
    }
}
