package com.orderService.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orderService.model.Order;
import com.orderService.repository.OrderRepository;


@Service

public class OrderService {
    @Autowired
     private OrderRepository orderRepository;
    @Autowired
    private OrderEventPublisher eventPublisher;

    public Order addOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        eventPublisher.publishOrderCreatedEvent(savedOrder);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long OrderID) {
        orderRepository.deleteById(OrderID);
    }
}