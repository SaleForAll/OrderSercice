package com.orderService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.orderService.openFeignClient.InventoryClient;

@Component
public class InventoryServiceHealthIndicator implements HealthIndicator {
    @Autowired
    private InventoryClient inventoryClient;

    @Override
    public Health health() {
        try {
            inventoryClient.getProductStockFromInvt(1);
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("Error", e.getMessage()).build();
        }
    }
}