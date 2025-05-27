package com.orderService.openFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderService.model.Inventory;

@FeignClient(name = "INVENTORY-SERVICE", url = "http://localhost:2222/api/inventory")
public interface InventoryClient {

    @GetMapping("/{id}")
    public Inventory getProductStockFromInvt(@PathVariable Integer id);
    

    @PutMapping("/update/{id}")
    public Inventory updateReservedtStockToInvt(@PathVariable Integer id,@RequestBody Inventory inventory);

}