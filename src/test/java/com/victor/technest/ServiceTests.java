package com.victor.technest;

import com.victor.technest.model.Account;
import com.victor.technest.repository.AccountRepository;
import com.victor.technest.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @InjectMocks
    private static AccountService service;

    @Mock
    private AccountRepository repository;

    private Account account;

    @Test
    void getAllAccountsTest() {
        Mockito.when(repository.findAll()).thenReturn(List.of(account));
        Assertions.assertEquals(1, service.getAllAccounts().size());
    }

    @Test
    void getAccountsByIdTest() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        Assertions.assertEquals(service.getAccountById(1L), account);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> service.getAccountById(1L));
    }

    @BeforeEach
    void init() {
        account = new Account();
        account.setId(1L);
        account.setName("Name");
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(Currency.getInstance("EUR"));
        account.setTreasury(true);
    }
}
