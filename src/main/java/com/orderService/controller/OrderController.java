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
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/getOrderbyId/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PutMapping("/modifyOrder/{id}")
    // public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
    //     try {
    //         return ResponseEntity.ok(orderService.updateOrder(id, order));
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(409).build(); 
    //     }
    // }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order has been deleted successfully");
    }

    // @ExceptionHandler(value = InsufficientStockException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public ErrorResponse handleNoSuchCustomerExistsException(InsufficientStockException ex) {
    //     return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    // }
}