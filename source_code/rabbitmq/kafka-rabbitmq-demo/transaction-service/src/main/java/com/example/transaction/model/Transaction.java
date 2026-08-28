package com.example.transaction.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a banking transaction event.
 * Transferred as Event-Carried State Transfer payload so consumers
 * have all data they need without calling back to the producer.
 */
public class Transaction {

    private final Long id;
    private final Long userId;
    private final double amount;
    /** e.g. DEBIT or CREDIT */
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
