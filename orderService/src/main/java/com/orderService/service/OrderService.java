package com.orderService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderService.model.Order;
import com.orderService.repository.OrderRepository;


@Service

public class OrderService {
    @Autowired
     private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long OrderID) {
        return orderRepository.findById(OrderID);
    }

    public void deleteOrder(Long OrderID) {
        orderRepository.deleteById(OrderID);
    }
}
