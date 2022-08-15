package com.example.server2.controller;

import com.example.server2.feign.OrderClient;
import com.example.server2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gyh on 2022/6/20
 */
@RestController
@RequestMapping("order")
public class OrderController implements OrderClient {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Integer create(@RequestParam("accountId") Integer accountId,
                          @RequestParam("number") Integer number,
                          @RequestParam("name") String name) {
        return orderService.create(accountId, number, name);
    }

    @GetMapping("/update")
    public Integer update(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        return orderService.update(id, name);
    }

    @GetMapping("/repetition")
    public Integer repetition(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        return orderService.repetition(id, name);
    }
}
