package com.orderService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderService.model.Order;
import com.orderService.service.OrderService;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<Order> createProduct(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.addOrder(order));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @DeleteMapping("/{OrderID}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long OrderID) {
        orderService.deleteOrder(OrderID);
        return ResponseEntity.ok("Order deleted successfully");
    }
}