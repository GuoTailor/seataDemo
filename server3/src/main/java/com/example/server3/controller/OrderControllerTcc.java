package com.example.server3.controller;

import com.example.server3.model.Order;
import com.example.server3.service.OrderServiceTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gyh on 2022/6/20
 */
@RestController
@RequestMapping("orderTcc")
public class OrderControllerTcc {
    @Autowired
    private OrderServiceTcc orderServiceTcc;

    @PostMapping("/create")
    public Integer prepare(@RequestBody Order order) throws InterruptedException {
        return orderServiceTcc.prepare(order);
    }

}
