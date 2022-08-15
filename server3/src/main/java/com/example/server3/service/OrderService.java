package com.example.server3.service;

import com.example.server3.mapper.OrderMapper;
import com.example.server3.model.Order;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by gyh on 2022/6/20
 */
@Service
@Slf4j
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public Integer create(Integer accountId, Integer number, String name) {
        Order order = new Order();
        order.setName(name);
        order.setAccountId(accountId);
        order.setNumber(number);
        return orderMapper.insertSelective(order);
    }

    @GlobalLock
    public Integer update(Integer id) {
        log.info("server3 >>>>> 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("server3 >>>>> 2");
        Order order = new Order();
        order.setId(id);
        order.setName("server3");
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @GlobalLock
    public Integer repetition(Integer id, String name) {
        log.info(name);
        Order order = new Order();
        order.setId(id);
        order.setName(name);
        int i = orderMapper.updateByPrimaryKeySelective(order);
        log.info(">>>>>>>>> {}", i);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("完成");
        return i;
    }
}
