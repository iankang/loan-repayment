package com.loaning.loanrepay.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SubscriberRequest {

    private String username;
    private String phoneNumber;
}
