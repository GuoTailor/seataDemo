package com.example.server3.feign;

import com.example.server3.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by gyh on 2022/6/21
 */
@FeignClient(name = "server3", path = "orderTcc")
public interface OrderClientTcc {
    @PostMapping("/create")
    Integer prepare(@RequestBody Order order);

}
