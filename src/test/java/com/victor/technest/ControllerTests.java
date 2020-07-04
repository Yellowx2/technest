package com.victor.technest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.technest.model.Account;
import com.victor.technest.service.IAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@WebMvcTest
public class ControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private IAccount service;

    private Account account;

    private static final String ACCOUNT_PATH = "/account";
    private static final String ID_1 = "/1";
    private static final String TRANSFER = "/from/" + ID_1 + "/to/2/" + BigDecimal.ONE;

    @Test
    void getAllAccountsTest() throws Exception {
        var mapper = new ObjectMapper();
        given(service.getAllAccounts()).willReturn(List.of(account));

        mvc.perform(get(ACCOUNT_PATH)).andExpect(ResultMatcher.matchAll(
                status().isOk(),
                content().json(mapper.writeValueAsString(List.of(account)))));
    }

    @Test
    void getAccountTest() throws Exception {
        var mapper = new ObjectMapper();
        given(service.getAccountById(anyLong())).willReturn(account);

        mvc.perform(get(ACCOUNT_PATH + ID_1)).andExpect(ResultMatcher.matchAll(
                status().isOk(),
                content().json(mapper.writeValueAsString(account))));
    }

    @Test
    void createAccountTest() throws Exception {
        var mapper = new ObjectMapper();
        willDoNothing().given(service).createAccount(any(Account.class));

        mvc.perform(put(ACCOUNT_PATH).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(account)))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().string("Account with name '" + account.getName() + "' successfully created.")));
    }

    @Test
    void updateAccountTest() throws Exception {
        var mapper = new ObjectMapper();
        willDoNothing().given(service).updateAccount(any(Account.class), anyLong());

        mvc.perform(post(ACCOUNT_PATH + ID_1).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(account)))
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        content().string("Account with id '" + 1 + "' successfully updated.")));
    }

    @Test
    void deleteAccountTest() throws Exception {
        willDoNothing().given(service).deleteAccount(anyLong());

        mvc.perform(delete(ACCOUNT_PATH + ID_1)).andExpect(ResultMatcher.matchAll(
                status().isOk(),
                content().string("Account with id '" + 1 + "' successfully deleted.")));
    }

    @Test
    void transferTest() throws Exception {
        willDoNothing().given(service).transfer(anyLong(), anyLong(), any(BigDecimal.class));

        mvc.perform(post(ACCOUNT_PATH + TRANSFER)).andExpect(ResultMatcher.matchAll(
                status().isOk(),
                content().string("Transfer between accounts id 1 '" + 1 + "' and id 2 '" + 2 + "' successfully completed.")));
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
