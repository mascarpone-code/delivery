package com.mascarpone.delivery.ws.exception;

public class UnknownActionException extends RuntimeException {
    public UnknownActionException(String message) {
        super(message);
    }
}
