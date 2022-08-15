package com.example.server2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gyh on 2022/6/21
 */
@FeignClient(name = "server2", path = "order")
public interface OrderClient {
    @PostMapping("/create")
    Integer create(@RequestParam("accountId") Integer accountId,
                   @RequestParam("number") Integer number,
                   @RequestParam("name") String name);

    @GetMapping("/update")
    Integer update(@RequestParam("id") Integer id, @RequestParam("name") String name);

    @GetMapping("/repetition")
    Integer repetition(@RequestParam("id") Integer id, @RequestParam("name") String name);
}
