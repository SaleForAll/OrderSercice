package com.orderService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long InventoryID;
	private Integer ProductID;
	private Integer AvailableStock;
	private Integer ReservedStock;
	public Long getInventoryID() {
		return InventoryID;
	}
	public void setInventoryID(Long inventoryID) {
		InventoryID = inventoryID;
	}
	public Integer getProductID() {
		return ProductID;
	}
	public void setProductID(Integer productID) {
		ProductID = productID;
	}
	public Integer getAvailableStock() {
		return AvailableStock;
	}
	public void setAvailableStock(Integer availableStock) {
		AvailableStock = availableStock;
	}
	public Integer getReservedStock() {
		return ReservedStock;
	}
	public void setReservedStock(Integer reservedStock) {
		ReservedStock = reservedStock;
	}
	
	

}