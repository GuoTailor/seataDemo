package com.example.server1.service;

import com.example.server1.mapper.AccountMapper;
import com.example.server1.model.Account;
import com.example.server2.feign.OrderClientTcc;
import com.example.server2.model.Order;
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
public class AccountServiceTcc {
    @Resource
    private AccountMapper accountMapper;
    @Autowired
    private OrderClientTcc orderClientTcc;
    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    public void addTran() {
        Account account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
        assert account.getNumber() > 1;
        account.setNumber(account.getNumber() - 1);
        account.setFreeze(account.getFreeze() + 1);
        accountMapper.updateByPrimaryKeySelective(account);

        Order order = new Order();
        order.setAccountId(account.getId());
        order.setNumber(account.getNumber());
        order.setName(account.getName());
        Integer integer = orderClientTcc.prepare(order);
        log.info("远程调用 {}", integer);
        account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
    }


    public void add() {
        Account account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
        assert account.getNumber() > 1;
        account.setNumber(account.getNumber() - 1);
        account.setFreeze(account.getFreeze() + 1);
        accountMapper.updateByPrimaryKeySelective(account);

        Order order = new Order();
        order.setAccountId(account.getId());
        order.setNumber(account.getNumber());
        order.setName(account.getName());
        Integer integer = orderClientTcc.prepare(order);
        log.info("远程调用 {}", integer);
        if (integer == 1)
            throw new IllegalArgumentException();
        account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
    }
}
