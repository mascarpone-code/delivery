package com.mascarpone.delivery.ws.exception;

public class GeneralSocketException extends RuntimeException{
    public GeneralSocketException(String message) {
        super(message);
    }

    public GeneralSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
