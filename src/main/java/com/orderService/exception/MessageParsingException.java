package com.orderService.exception;

public class MessageParsingException extends RuntimeException {
    public MessageParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}