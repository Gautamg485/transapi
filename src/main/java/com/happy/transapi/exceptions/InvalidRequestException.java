package com.happy.transapi.exceptions;

import org.springframework.validation.BindingResult;

public class InvalidRequestException extends RuntimeException {
    private BindingResult bindingResult;

    public InvalidRequestException(BindingResult bindingResult) {
        super("Invalid Request Body");
        this.bindingResult = bindingResult;
    }
    public BindingResult getBindingResult() {
        return this.bindingResult;
    }
}