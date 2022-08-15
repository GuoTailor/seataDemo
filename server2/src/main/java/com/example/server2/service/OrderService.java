package com.example.server2.service;

import com.example.server2.mapper.OrderMapper;
import com.example.server2.model.Order;
import com.example.server3.feign.OrderClient;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gyh on 2022/6/20
 */
@Service
@Slf4j
public class OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private
    OrderClient orderClient3;

    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    public Integer create(Integer accountId, Integer number, String name) {
        Order order = new Order();
        order.setName(name);
        order.setAccountId(accountId);
        order.setNumber(number);
        return orderMapper.insertSelective(order);
    }

    @GlobalLock
    public Integer update(Integer id, String name) {
//        log.info("server2 >>>>> 1");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("server2 >>>>> 2");
        log.info(name);
        Order order = new Order();
        order.setId(id);
        order.setName(name);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @GlobalLock
    public Integer repetition(Integer id, String name) {
        log.info(name);
        Order order = new Order();
        order.setId(id);
        order.setName(name);
        int i = orderMapper.updateByPrimaryKeySelective(order);

        new Thread(()-> orderClient3.repetition(36, "server3")).start();
        orderClient3.repetition(36, "server3");
        /*try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        if (i == 1) throw new IllegalArgumentException();
        return i;
    }

}
