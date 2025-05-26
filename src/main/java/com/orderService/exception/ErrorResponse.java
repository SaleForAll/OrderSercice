package com.orderService.exception;

public class ErrorResponse {
    @SuppressWarnings("FieldMayBeFinal")
    private int status;
    @SuppressWarnings("FieldMayBeFinal")
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}