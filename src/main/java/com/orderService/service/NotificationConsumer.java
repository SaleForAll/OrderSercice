package com.orderService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderService.model.Order;

@Component
public class NotificationConsumer {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order.created", groupId = "notification-service")
    public void consumeOrderCreated(String message) {
        logger.info("Received order event: {}", message);
        try {
            Order order = objectMapper.readValue(message, Order.class);
            sendEmailNotification(order);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse message: {}", message, e);
        }
    }

    private void sendEmailNotification(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("naresh.shivangi@gmail.com");
        message.setTo(order.getCustomerEmail()); // Ensure OrderEvent has this field
        message.setSubject("Your Order Has Been Created!");
        message.setText("Dear " + order.getCustomerName() + ",\n\n"
                + "Your order with ID " + order.getOrderID() + " has been successfully created.\n\n"
                + "Thank you for shopping with us!");

        mailSender.send(message);
    }
}