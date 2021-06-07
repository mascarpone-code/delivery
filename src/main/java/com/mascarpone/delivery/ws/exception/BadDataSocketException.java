package com.mascarpone.delivery.ws.exception;

public class BadDataSocketException extends RuntimeException{
    public BadDataSocketException(String message) {
        super(message);
    }

    public BadDataSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
