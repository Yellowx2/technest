package com.victor.technest.service;

import com.victor.technest.model.Account;
import com.victor.technest.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccount {

    @Autowired
    private AccountRepository repository;

    @Override
    public List<Account> getAllAcounts() {
        return repository.findAll();
    }
}
