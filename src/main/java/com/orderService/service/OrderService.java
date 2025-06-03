package com.orderService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderService.exception.InsufficientStockException;
import com.orderService.exception.OrderNotFoundException;
import com.orderService.model.Inventory;
import com.orderService.model.Order;
import com.orderService.model.User;
import com.orderService.openFeignClient.InventoryClient;
import com.orderService.openFeignClient.UserClient;
import com.orderService.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryClient inventoryClient;
    //  @Autowired
    // private OrderEventPublisher eventPublisher;
     @Autowired
    private UserClient userClient;

    @CircuitBreaker(name = "INVENTORY-SERVICE", fallbackMethod ="getAllAvailableProducts")
    public Order createOrder(Order order) {
      //Search the available current stock
       Integer enteredStock = order.getOrderQty();
        Inventory inventory = inventoryClient.getProductStockFromInvt(enteredStock);
        int currentStock = inventory.getAvailableStock() - inventory.getReservedStock();
        
        if (currentStock < enteredStock) {
            throw new InsufficientStockException("Insufficient current stock of " + currentStock + " and the order qty entered was " + order.getOrderQty());
        }
        order.setStatus("CREATED");
        //check user details based on id 
        User user = getUserDetailbyUserID(order.getUserId());
        order.setCustomerName(user.getName());
        order.setCustomerEmail(user.getEmail());
        Order savedOrder = orderRepository.save(order); // Save the order first
        // eventPublisher.publishOrderCreatedEvent(savedOrder);
        // Update reserved stock
        inventory.setReservedStock(enteredStock);
        inventoryClient.updateReservedtStockToInvt(order.getOrderQty(),inventory);
        return savedOrder;

   }

    public Order getAllAvailableProducts(Exception e) {
    return new Order(1, 1L, 1, "Car", 99990.0, "Fallback-Sample", "Shivangi", "shivangi@gmail.com");
   }

   @CircuitBreaker(name = "USER-SERVICE", fallbackMethod ="tryLoginAgain")
   public User getUserDetailbyUserID(Long userId){
    return userClient.getUserDetailbyUserID(userId);
   }

   public User tryLoginAgain(Exception e){
    return new User(1L,"dummy","fallback-method of userService", "");
   }
   

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setProductList(updatedOrder.getProductList());
            order.setTotalPrice(updatedOrder.getTotalPrice());
            order.setStatus(updatedOrder.getStatus());
            try {
                return orderRepository.save(order);
            } catch (OptimisticLockingFailureException e) {
                throw new RuntimeException("Conflict: Order was modified by another transaction. Please retry.", e);
            }
        }).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }

}
