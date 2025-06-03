package com.orderService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderService.exception.MessageParsingException;
import com.orderService.exception.NotificationSendException;
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
        Order order = parseOrderMessage(message);  // Can throw MessageParsingException
        sendEmailNotification(order);              // Can throw NotificationSendException
    }

    private Order parseOrderMessage(String message) {
        try {
            return objectMapper.readValue(message, Order.class);
        } catch (JsonProcessingException e) {
            throw new MessageParsingException("Failed to parse order message: " + message, e);
        }
    }

    private void sendEmailNotification(Order order) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom("naresh.shivangi@gmail.com");
            email.setTo(order.getCustomerEmail());
            email.setSubject("Your Order Has Been Created!");
            email.setText("Dear " + order.getCustomerName() + ",\n\n"
                    + "Your order with ID " + order.getOrderID() + "and below order details:  \n\n"
                    + "customer name: " + order.getCustomerName()+ ",\n\n" 
                    + "customer email: "+ order.getCustomerEmail() + ",\n\n"
                    +  "order Quantity: " + order.getOrderQty() + ",\n\n" 
                    +  "Totalprice :" + order.getTotalPrice() + ",\n\n" 
                    +  "product list: "+  order.getProductList() + "\n\n" 
                    + " has been successfully created.\n\n"
                    + "Thank you for shopping with us!");

            mailSender.send(email);
        } catch (MailException e) {
            throw new NotificationSendException("Failed to send email notification for order ID: " + order.getOrderID(), e);
        }
    }
}