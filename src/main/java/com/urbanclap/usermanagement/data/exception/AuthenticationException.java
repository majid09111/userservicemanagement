package com.urbanclap.usermanagement.data.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException{
    private final HttpStatus httpStatusCode;
    public AuthenticationException(String message, HttpStatus status){
        super(message);
        this.httpStatusCode=status;
    }
    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
