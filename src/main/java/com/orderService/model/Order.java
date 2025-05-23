package com.orderService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="orders") 
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;
    @Column(name = "UserID")
    private Long userId;  // ✅ camelCase field name

    private String productList;
    private Double totalPrice;
    private String status;

    //kafka
    private String customerName;
    private String customerEmail;

}