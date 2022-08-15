package com.example.server1.controller;

import com.example.server1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gyh on 2022/6/22
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/updateA")
    public void updateA() {
        accountService.updateA();
    }

    @GetMapping("/updateB")
    public void updateB() {
        accountService.updateB();
    }

    @GetMapping("/repetition")
    public void repetition() {
        accountService.repetition();
    }

}
