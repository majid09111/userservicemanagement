package com.urbanclap.usermanagement.data.exception;

import org.springframework.http.HttpStatus;

public class ConsumerException extends RuntimeException{

    private final HttpStatus httpStatusCode;
    public ConsumerException(String message, HttpStatus status){
        super(message);
        this.httpStatusCode =status;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
