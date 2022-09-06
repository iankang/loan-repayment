package com.loaning.loanrepay.exceptions;

public class LoanException extends RuntimeException{

    private String message;

    public LoanException(String message) {
        this.message = message;
    }

    public LoanException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
