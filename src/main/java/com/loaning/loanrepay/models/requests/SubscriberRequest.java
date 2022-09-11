package com.loaning.loanrepay.models.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriberRequest {

    private String username;
    private String phoneNumber;
}
