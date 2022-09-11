package com.loaning.loanrepay.exceptions;
public class ConflictException extends RuntimeException{

    String message;

    public ConflictException(String message) {
        super(message);
        this.message = message;
    }
}
