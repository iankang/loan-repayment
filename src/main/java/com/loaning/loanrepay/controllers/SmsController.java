package com.loaning.loanrepay.controllers;

import com.loaning.loanrepay.services.SMSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    private SMSService smsService;

    public SmsController(SMSService smsService) {
        this.smsService = smsService;
    }

    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS() {

       return smsService.sendSMS();
    }
}