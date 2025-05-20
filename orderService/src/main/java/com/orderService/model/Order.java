package com.orderService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="orders") 
public class Order {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long OrderID;
    private Long UserID;
	private String ProductList;
	private Double TotalPrice;
	private String Status;


   public Long getOrderID() {
        return OrderID;
    }

    public void setOrderID(Long OrderID) {
        this.OrderID = OrderID;
    }


    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long UserID) {
        this.UserID = UserID;
    }

    public String getProductList() {
        return ProductList;
    }

    public void setProductList(String ProductList) {
        this.ProductList = ProductList;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

 

}