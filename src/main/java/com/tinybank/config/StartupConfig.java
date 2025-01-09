package com.tinybank.config;

import com.tinybank.model.Account;
import com.tinybank.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class StartupConfig {

    @Bean
    public CommandLineRunner initializeAccount(AccountRepository accountRepository) {
        return args -> {
            // Check if an account already exists
            if (accountRepository.count() == 0) {
                // Create a new account with an initial balance of 0
                Account account = new Account();
                account.setBalance(BigDecimal.ZERO);
                accountRepository.save(account);
                System.out.println("Initialized account with ID: " + account.getId());
            } else {
                System.out.println("Account already exists. Skipping initialization.");
            }
        };
    }
}
