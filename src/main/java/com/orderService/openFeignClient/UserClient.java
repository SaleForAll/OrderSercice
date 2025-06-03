package com.orderService.openFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.orderService.model.User;


@FeignClient(name = "USER-SERVICE", url = "http://localhost:2224/api/users")
public interface UserClient {

    @GetMapping("/{userId}")
    public User getUserDetailbyUserID(@PathVariable Long userId);

}