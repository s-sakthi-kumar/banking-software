package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "accounts", key = "#id")
    public Account getAccount(Long id) {

        System.out.println("Fetching account from DATABASE...");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    public Account createAccount(Account account) {
        return repository.save(account);
    }

    @CacheEvict(value = "accounts", key = "#id")
    public Account deposit(Long id, double amount) {

        Account account = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);

        return repository.save(account);
    }

    @CacheEvict(value = "accounts", key = "#id")
    public Account withdraw(Long id, double amount) {

        Account account = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        return repository.save(account);
    }
}
