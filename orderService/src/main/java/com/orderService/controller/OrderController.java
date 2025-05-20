package com.orderService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderService.model.Order;
import com.orderService.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{UserID}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long UserID) {
        return ResponseEntity.ok(orderService.getOrderById(UserID)
                .orElseThrow(() -> new RuntimeException("Order not found")));
    }

    @DeleteMapping("/{UserID}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long UserID) {
        orderService.deleteOrder(UserID);
        return ResponseEntity.ok("Order deleted successfully");
    }
}