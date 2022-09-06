package com.loaning.loanrepay.controllers;

import com.loaning.loanrepay.entitites.Subscriber;
import com.loaning.loanrepay.models.requests.SubscriberRequest;
import com.loaning.loanrepay.models.responses.PagedResponse;
import com.loaning.loanrepay.services.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscriber")
@Tag(name = "Subscriber", description = "This manages the subscriber in the system")
public class SubscriberController {

    private final Logger logger = LoggerFactory.getLogger(SubscriberController.class);

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {

        this.subscriberService = subscriberService;
    }

    @Operation(summary = "Add a subscriber", description = "Adds a subscriber to the system", tags = {"Subscriber"})
    @PostMapping("/addSubscriber")
    public ResponseEntity<?> addSubscriber(
            @Parameter(description = "subscriber details", required = true)
            @RequestBody SubscriberRequest subscriberRequest
    ){

        return  subscriberService.addSubscriber(subscriberRequest);
    }

    @Operation(summary = "Get all subscribers", description = "Gets all subscribers in the system", tags = {"Subscriber"})
    @GetMapping("/getAllSubscribers")
    public ResponseEntity<PagedResponse<Subscriber>> getAllSubscribers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return subscriberService.getAllSubscribers(page, size);
    }

    @Operation(summary = "Get a single subscribers", description = "Gets a single subscriber in the system", tags = {"Subscriber"})
    @GetMapping("/getSubscriber")
    public ResponseEntity<Subscriber> getSubscriberByPhoneNumber(
            @Parameter(description = "phone number of subscriber you want", required = true)
           @RequestParam("phoneNumber") String phoneNumber
    ){
        return  subscriberService.getSingleSubscriberByPhoneNumber(phoneNumber);
    }

    @Operation(summary = "Delete a single subscribers", description = "Deletes a single subscriber in the system", tags = {"Subscriber"})
    @DeleteMapping("/deleteSubscriber")
    public ResponseEntity<HttpStatus> deleteSubscriber(
            @Parameter(description = "phone number of subscriber you want to delete", required = true)
            @RequestParam("phoneNumber") String phoneNumber){
        return subscriberService.deleteSubscriber(phoneNumber);
    }
}
