package com.mascarpone.delivery.ws.exception;

public class AuthSocketException extends RuntimeException{
    public AuthSocketException(String message) {
        super(message);
    }

    public AuthSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
