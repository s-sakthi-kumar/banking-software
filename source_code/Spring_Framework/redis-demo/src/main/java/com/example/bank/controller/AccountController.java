package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return service.createAccount(account);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(
            @PathVariable Long id,
            @RequestParam double amount) {

        return service.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(
            @PathVariable Long id,
            @RequestParam double amount) {

        return service.withdraw(id, amount);
    }
}
