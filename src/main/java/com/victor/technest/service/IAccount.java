package com.victor.technest.service;

import com.victor.technest.model.Account;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public interface IAccount {

    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    void createAccount(Account account);
    void updateAccount(Account account, Long id);
    void deleteAccount(Long id);
    void transfer(Long origin, Long destination, BigDecimal amount);
}
