package com.example.server1.service;

import com.example.server1.mapper.AccountMapper;
import com.example.server1.model.Account;
import com.example.server2.feign.OrderClient;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gyh on 2022/6/20
 */
@Service
@Slf4j
public class AccountService {
    @Resource
    private AccountMapper accountMapper;
    @Autowired
    private OrderClient orderClient;

    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    public void add() {
        Account account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
        assert account.getNumber() > 1;
        account.setNumber(account.getNumber() - 1);
        account.setFreeze(account.getFreeze() + 1);
        accountMapper.updateByPrimaryKeySelective(account);

        Integer integer = orderClient.create(account.getId(), account.getNumber(), account.getName());
        log.info("远程调用 {}", integer);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (integer == 1)
            throw new IllegalArgumentException();
        account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
    }

    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    public void updateA() {
        log.info("server2");
        Integer update = orderClient.update(6, "server2");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (update == 1) {
            throw new IllegalArgumentException();
        }
        System.out.println(update);
    }

//    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    public void updateB() {
        log.info("server3");
        Integer update = orderClient.update(6, "server3");
        System.out.println(update);
    }

    @GlobalTransactional(timeoutMills = 50_000, rollbackFor = Exception.class)
    @Transactional
    @GlobalLock
    public void repetition() {
        log.info("repetition");

        Account account = accountMapper.selectByPrimaryKey(1);
        log.info(account.toString());
        assert account.getNumber() > 1;
        account.setNumber(account.getNumber() - 1);
        account.setFreeze(account.getFreeze() + 1);
        int i = accountMapper.updateByPrimaryKeySelective(account);
        log.info("更新 {}", i);
        orderClient.repetition(6, "server2");


        List<Integer> server2 = Arrays.asList(1,2,3,4,5,6,7,8,9,7).parallelStream()
                .peek(it -> log.info(Thread.currentThread().getName()))
                .filter(it -> it == 3)
                .map(it -> orderClient.repetition(6, "server2"))
                .collect(Collectors.toList());
//        Integer update = orderClient.repetition(6, "server2");
        /*if (update == 1) {
            throw new IllegalArgumentException();
        }*/

        System.out.println(server2);
    }

}
