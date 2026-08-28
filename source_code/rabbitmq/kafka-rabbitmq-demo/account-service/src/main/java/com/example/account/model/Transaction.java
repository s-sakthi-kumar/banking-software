package com.example.account.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mirror of the Transaction model used by the account-service consumer.
 * In a real system this would live in a shared library.
 */
public class Transaction {

    private final Long id;
    private final Long userId;
    private final double amount;
    private final String type;

    @JsonCreator
    public Transaction(
            @JsonProperty("id")     Long id,
            @JsonProperty("userId") Long userId,
            @JsonProperty("amount") double amount,
            @JsonProperty("type")   String type) {
        this.id     = id;
        this.userId = userId;
        this.amount = amount;
        this.type   = type;
    }

    public Long getId()     { return id; }
    public Long getUserId() { return userId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", userId=" + userId
                + ", amount=" + amount + ", type=" + type + "}";
    }
}
