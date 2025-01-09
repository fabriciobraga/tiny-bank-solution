package com.tinybank.controller;

import com.tinybank.model.Transaction;
import com.tinybank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Deposit money into the account", description = "Adds the specified amount to the account balance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid deposit amount")
    })
    @PostMapping("/deposit")
    public BigDecimal deposit(@RequestParam BigDecimal amount) {
        return accountService.deposit(amount);
    }

    @Operation(summary = "Withdraw money from the account", description = "Subtracts the specified amount from the account balance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Invalid withdrawal amount or insufficient balance")
    })
    @PostMapping("/withdraw")
    public BigDecimal withdraw(@RequestParam BigDecimal amount) {
        return accountService.withdraw(amount);
    }

    @Operation(summary = "Get the current account balance", description = "Retrieves the current balance of the account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully")
    })
    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return accountService.getBalance();
    }

    @Operation(summary = "Get the transaction history", description = "Retrieves the list of all transactions for the account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully")
    })
    @GetMapping("/transactions")
    public List<Transaction> getTransactionHistory() {
        return accountService.getTransactionHistory();
    }
}
