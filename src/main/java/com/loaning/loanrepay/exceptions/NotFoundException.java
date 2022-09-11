package com.loaning.loanrepay.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException{

    String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
