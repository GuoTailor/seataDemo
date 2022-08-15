package com.example.server2.service;

import com.alibaba.fastjson.JSONObject;
import com.example.server2.mapper.OrderMapper;
import com.example.server2.model.Order;
import com.example.server3.feign.OrderClientTcc;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gyh on 2022/6/20
 */
@Service
@Slf4j
public class OrderServiceTcc implements TccActionOne {
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private OrderClientTcc orderClientTcc;

    @Override
    public Integer prepare(Order order) {
        log.info("prepare >>>>>>>>>>" + Thread.currentThread().getName());
        log.info("xid = " + RootContext.getXID());
        log.info(order.toString());
        int i = orderMapper.insertSelective(order);
        com.example.server3.model.Order order1 = new com.example.server3.model.Order();
        BeanUtils.copyProperties(order, order1);
        order1.setId(null);
        orderClientTcc.prepare(order1);

        return i;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("commit <<<<<<<<<" + Thread.currentThread().getName());
        JSONObject order = (JSONObject) actionContext.getActionContext("order");
        log.info(order.toString());
        Order order1 = order.toJavaObject(Order.class);
        order1.setName(order1.getName() + " commit");
        orderMapper.updateByPrimaryKeySelective(order1);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("rollback <<<<<<<<<" + Thread.currentThread().getName());
        JSONObject order = (JSONObject) actionContext.getActionContext("order");
        log.info(order.toString());
        Order order1 = order.toJavaObject(Order.class);
        order1.setName(order1.getName() + " rollback");
        orderMapper.updateByPrimaryKeySelective(order1);
        return true;
    }
}
