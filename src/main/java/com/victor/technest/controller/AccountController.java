package com.victor.technest.controller;

import com.victor.technest.model.Account;
import com.victor.technest.service.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccount accounts;

    @GetMapping("")
    public List<Account> getAllAccounts() {
        return accounts.getAllAcounts();
    }
}
