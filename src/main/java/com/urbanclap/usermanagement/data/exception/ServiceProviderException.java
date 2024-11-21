package com.urbanclap.usermanagement.data.exception;

import org.springframework.http.HttpStatus;

public class ServiceProviderException extends RuntimeException {
    private final HttpStatus httpStatusCode;
    public ServiceProviderException(String message, HttpStatus status){
        super(message);
        this.httpStatusCode=status;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
