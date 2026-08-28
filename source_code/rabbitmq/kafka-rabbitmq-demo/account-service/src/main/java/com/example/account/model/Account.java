package com.example.account.model;

/**
 * Simple in-memory account representation.
 * In production this would be a JPA entity backed by a database.
 */
public class Account {

    private final Long userId;
    private double balance;

    public Account(Long userId, double initialBalance) {
        this.userId  = userId;
        this.balance = initialBalance;
    }

    public Long getUserId() { return userId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Account{userId=" + userId + ", balance=" + balance + "}";
    }
}
