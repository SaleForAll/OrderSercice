package com.orderService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderService.exception.InsufficientStockException;
import com.orderService.model.Inventory;
import com.orderService.model.Order;
import com.orderService.openFeignClient.InventoryClient;
import com.orderService.repository.OrderRepository;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryClient inventoryClient;

    public Order createOrder(Order order) {
      //Search the available current stock
       Integer enteredStock = order.getOrderQty();
        Inventory inventory = inventoryClient.getProductStockFromInvt(enteredStock);
        int currentStock = inventory.getAvailableStock() - inventory.getReservedStock();
        
        if (currentStock < enteredStock) {
            throw new InsufficientStockException("Insufficient current stock of " + currentStock + " and the order qty entered was " + order.getOrderQty());
        }
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order); // Save the order first
        // Update reserved stock
        inventory.setReservedStock(enteredStock);
        inventoryClient.updateReservedtStockToInvt(order.getOrderQty(),inventory);
        return savedOrder;




    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // public Order updateOrder(Long id, Order updatedOrder) {
    //     return orderRepository.findById(id).map(order -> {
    //         order.setProductList(updatedOrder.getProductList());
    //         order.setTotalPrice(updatedOrder.getTotalPrice());
    //         order.setStatus(updatedOrder.getStatus());
    //         try {
    //             return orderRepository.save(order);
    //         } catch (OptimisticLockingFailureException e) {
    //             throw new RuntimeException("Conflict: Order was modified by another transaction. Please retry.", e);
    //         }
    //     }).orElseThrow(() -> new RuntimeException("Order not found"));
    // }
    
    public void deleteOrder(Long OrderID) {
        orderRepository.deleteById(OrderID);
    }
}
