package com.orderService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderService.model.Order;
import com.orderService.repository.OrderRepository;


@Service

public class OrderService {
    @Autowired
     private OrderRepository orderRepository;

     public Order addOrder(Order order) {
        return orderRepository.save(order);
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
