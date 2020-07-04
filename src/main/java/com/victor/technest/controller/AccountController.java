package com.victor.technest.controller;

import com.victor.technest.model.Account;
import com.victor.technest.service.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccount accounts;

    @GetMapping("")
    public List<Account> getAllAccounts() {
        return accounts.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable long id) {
        return accounts.getAccountById(id);
    }

    @PostMapping("")
    public void createAccount(@RequestBody Account account) {
        accounts.createAccount(account);
    }

    @PostMapping("/{id}")
    public void updateAccount(@PathVariable long id, @RequestBody Account account) {
        accounts.updateAccount(account, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accounts.deleteAccount(id);
    }
}
