package com.victor.technest.service;

import com.victor.technest.model.Account;
import com.victor.technest.repository.AccountRepository;
import com.victor.technest.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class AccountService implements IAccount {

    @Autowired
    private AccountRepository repository;

    private HashMap<String, BigDecimal> exchange = AccountUtils.createExchangeMap();

    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    @Override
    public void createAccount(Account account) {
        if (account.getTreasury() == null || (!account.getTreasury() && isNegativeBalance(BigDecimal.ZERO, account.getBalance())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Accounts with treasury set to FALSE can't have negative balance");

        repository.save(account);
    }

    @Override
    public void updateAccount(Account account, Long id) {

        var accountToUpdate = repository.getOne(id);

//        if (accountToUpdate.getTreasury() != null && !accountToUpdate.getTreasury().equals(account.getTreasury()))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update treasury");

        if (!accountToUpdate.getTreasury() && isNegativeBalance(accountToUpdate.getBalance(), account.getBalance()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update balance without treasury");

        if (StringUtils.hasText(account.getName()))
            accountToUpdate.setName(account.getName());

        if (StringUtils.hasText(account.getCurrency().toString()))
            accountToUpdate.setCurrency(account.getCurrency());

        if (account.getBalance() != null)
            accountToUpdate.setBalance(account.getBalance());

        repository.save(accountToUpdate);
    }

    @Override
    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void transfer(Long origin, Long destination, BigDecimal amount) {
        var originAccount = repository.getOne(origin);
        var destinationAccount = repository.getOne(destination);

        if (!originAccount.getTreasury() && isNegativeBalance(originAccount.getBalance(), amount.negate()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount to transfer can't be higher than " + originAccount.getBalance());

        /* Check accounts currency */
        if (originAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            originAccount.setBalance(originAccount.getBalance().subtract(amount));
            destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
        } else {
            originAccount.setBalance(originAccount.getBalance().subtract(amount));
            destinationAccount.setBalance(destinationAccount.getBalance().multiply(
                    exchange.get(originAccount.getCurrency() + "-" + destinationAccount.getCurrency())
            ));
        }

        repository.saveAll(List.of(originAccount, destinationAccount));
    }

    private boolean isNegativeBalance(BigDecimal original, BigDecimal add) {
        return original.add(add).signum() == -1;
    }
}
