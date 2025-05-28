package com.orderService.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(InsufficientStockException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFound(OrderNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(MessageParsingException.class)
    public void handleMessageParsingException(MessageParsingException ex) {
        logger.error("Message parsing failed: {}", ex.getMessage(), ex);
        // Optionally: Send alert, dead-letter the message, etc.
    }

    @ExceptionHandler(NotificationSendException.class)
    public void handleNotificationSendException(NotificationSendException ex) {
        logger.error("Notification sending failed: {}", ex.getMessage(), ex);
        // Optionally: Retry logic, alert, etc.
    }

    @ExceptionHandler(ListenerExecutionFailedException.class)
    public void handleKafkaListenerException(ListenerExecutionFailedException ex) {
        logger.error("Kafka listener execution failed: {}", ex.getMessage(), ex);
    }

    @ExceptionHandler(Exception.class)
    public void handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
    }
    @ExceptionHandler(KafkaPublishException.class)
    public void handleKafkaPublishException(KafkaPublishException ex) {
        logger.error("Kafka publish failed: {}", ex.getMessage(), ex);
        // Optionally: send alerts, retries, etc.
    }
}