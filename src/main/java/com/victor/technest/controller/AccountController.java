package com.victor.technest.controller;

import com.victor.technest.model.Account;
import com.victor.technest.service.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

    @PutMapping("")
    public String createAccount(@RequestBody Account account) {
        accounts.createAccount(account);
        return "Account with id '" + account.getId() + "' successfully created.";
    }

    @PostMapping("/{id}")
    public String updateAccount(@PathVariable long id, @RequestBody Account account) {
        accounts.updateAccount(account, id);
        return "Account with id '" + id + "' successfully updated.";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accounts.deleteAccount(id);
        return "Account with id '" + id + "' successfully deleted.";
    }

    @PostMapping("/from/{origin}/to/{destination}/{amount}")
    public String transfer(@PathVariable Long origin, @PathVariable Long destination, @PathVariable BigDecimal amount) {
        accounts.transfer(origin, destination, amount);
        return "Transfer between accounts id 1 '" + origin + "' and id 2 '" + destination + "' successfully completed.";
    }
}
