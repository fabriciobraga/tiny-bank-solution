package com.tinybank.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tinybank.exception.InsufficientBalanceException;
import com.tinybank.model.Transaction;
import com.tinybank.service.AccountService;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
    	mockTransaction = Transaction.builder()
    		    .id(1L)
    		    .type("DEPOSIT")
    		    .amount(BigDecimal.valueOf(100.00))
    		    .timestamp(LocalDateTime.now())
    		    .account(null) // Mocked account or null depending on your test scenario
    		    .build();

    }

    @Test
    void testDeposit() throws Exception {
        when(accountService.deposit(any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(100.00));

        mockMvc.perform(post("/api/account/deposit")
                .param("amount", "100.00"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    void testWithdraw() throws Exception {
        when(accountService.withdraw(any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(50.00));

        mockMvc.perform(post("/api/account/withdraw")
                .param("amount", "50.00"))
                .andExpect(status().isOk())
                .andExpect(content().string( "50.0"));
    }

    @Test
    void testGetBalance() throws Exception {
        when(accountService.getBalance()).thenReturn(BigDecimal.valueOf(100.00));

        mockMvc.perform(get("/api/account/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("100.0"));
    }

    @Test
    void testGetTransactionHistory() throws Exception {
        List<Transaction> mockTransactions = Arrays.asList(mockTransaction);
        when(accountService.getTransactionHistory()).thenReturn(mockTransactions);

        mockMvc.perform(get("/api/account/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].amount").value(100.0));
    }
    
    @Test
    void testWithdrawInsufficientBalance() throws Exception {
        // Mock insufficient balance scenario
        Mockito.doThrow(new InsufficientBalanceException("Insufficient balance"))
                .when(accountService).withdraw(Mockito.any(BigDecimal.class));

        mockMvc.perform(post("/api/account/withdraw")
                .param("amount", "100.00"))
                .andExpect(status().isBadRequest()) // Expect HTTP 400
                .andExpect(jsonPath("$.error").value("insufficient_balance"))
                .andExpect(jsonPath("$.message").value("Insufficient balance"));
    }

}
