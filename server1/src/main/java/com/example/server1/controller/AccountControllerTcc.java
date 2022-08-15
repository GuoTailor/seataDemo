package com.example.server1.controller;

import com.example.server1.service.AccountServiceTcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gyh on 2022/6/22
 */
@RestController
@RequestMapping("/accountTcc")
public class AccountControllerTcc {
    @Autowired
    AccountServiceTcc accountServiceTcc;

    @GetMapping("/addTran")
    public String addTran() {
        accountServiceTcc.addTran();
        return "OK";
    }

    @GetMapping("/add")
    public String add() {
        accountServiceTcc.add();
        return "OK";
    }
}
