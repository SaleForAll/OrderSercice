package com.orderService.exception;

public class InsufficientStockException extends RuntimeException {
    @SuppressWarnings("unused")
    private String message;
    public InsufficientStockException() {}

    public InsufficientStockException(String msg) {
        super(msg);
        this.message = msg;
    }
}