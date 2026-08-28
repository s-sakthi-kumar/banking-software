package com.example.account.service;

import com.example.account.model.Account;
import com.example.account.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core domain service shared by Kafka and RabbitMQ consumers.
 *
 * Idempotency guarantee
 * ---------------------
 * processedTransactions holds every transactionId that has already been
 * applied. If the same event is delivered twice (Kafka at-least-once,
 * RabbitMQ requeue on failure) we simply skip the duplicate.
 *
 * Event-Carried State Transfer
 * ----------------------------
 * The Transaction payload carries all state needed to update the account,
 * so the consumer never needs to call back to the transaction-service.
 */
@Service
public class AccountService {

    // In production these would be database repositories.
    private final Map<Long, Account> accounts         = new ConcurrentHashMap<>();
    private final Set<Long>          processedTxIds   = ConcurrentHashMap.newKeySet();

    public AccountService() {
        // Seed a few demo accounts
        accounts.put(101L, new Account(101L, 10_000.00));
        accounts.put(102L, new Account(102L,  5_000.00));
    }

    /**
     * Applies the transaction to the account balance.
     *
     * @param tx incoming transaction event
     * @return human-readable outcome
     */
    public String apply(Transaction tx) {

        // ── Idempotency check ──────────────────────────────────────────
        if (processedTxIds.contains(tx.getId())) {
            String msg = "[IDEMPOTENCY] Duplicate tx " + tx.getId() + " ignored.";
            System.out.println(msg);
            return msg;
        }

        // ── Upsert account if unknown ──────────────────────────────────
        accounts.computeIfAbsent(tx.getUserId(),
                uid -> new Account(uid, 0.0));

        Account account = accounts.get(tx.getUserId());

        // ── Apply balance update (Event-Carried State Transfer) ────────
        double before = account.getBalance();
        if ("CREDIT".equalsIgnoreCase(tx.getType())) {
            account.setBalance(before + tx.getAmount());
        } else {
            // Default: DEBIT
            account.setBalance(before - tx.getAmount());
        }

        // ── Mark as processed ──────────────────────────────────────────
        processedTxIds.add(tx.getId());

        String msg = "Applied " + tx + " | balance: " + before + " → " + account.getBalance();
        System.out.println(msg);
        return msg;
    }

    /** Expose current balance for a userId (used by the REST endpoint). */
    public Account getAccount(Long userId) {
        return accounts.get(userId);
    }
}
