package com.tinybank.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinybank.dto.DepositRequest;
import com.tinybank.exception.InsufficientBalanceException;
import com.tinybank.model.Account;
import com.tinybank.model.Transaction;
import com.tinybank.repository.AccountRepository;
import com.tinybank.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private static final Long DEFAULT_ACCOUNT_ID = 1L;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Deposit money into the default account.
     *
     * @param amount the amount to deposit
     * @return the updated balance
     */
    public BigDecimal deposit(DepositRequest depostiRequest) {
        Account account = getDefaultAccount();

        account.setBalance(account.getBalance().add(depostiRequest.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(depostiRequest.getAmount());
        transaction.setAccount(account);
        transaction.setLabel(depostiRequest.getLabel());

        transactionRepository.save(transaction);
        account.getTransactions().add(transaction);

        accountRepository.save(account);
        return account.getBalance();
    }

    /**
     * Withdraw money from the default account.
     *
     * @param amount the amount to withdraw
     * @return the updated balance
     */
    public BigDecimal withdraw(BigDecimal amount) {
        Account account = getDefaultAccount();

        if (amount.compareTo(account.getBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAWAL");
        transaction.setAmount(amount);
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        account.getTransactions().add(transaction);

        accountRepository.save(account);
        return account.getBalance();
    }

    public List<Transaction> findByLabelCointaingIgnoreCase(String keyword){
    	
    	return transactionRepository.findByLabelCointaingIgnoreCase(keyword);
    }

    /**
     * Get the current balance of the default account.
     *
     * @return the current balance
     */
    public BigDecimal getBalance() {
        return getDefaultAccount().getBalance();
    }

    /**
     * Get the transaction history of the default account.
     *
     * @return a list of transactions
     */
    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    /**
     * Retrieve the default account.
     *
     * @return the default account
     */
    private Account getDefaultAccount() {
        return accountRepository.findById(DEFAULT_ACCOUNT_ID)
                .orElseThrow(() -> new IllegalStateException("Default account not initialized!"));
    }
}
