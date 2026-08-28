package com.example.account.consumer;

import com.example.account.model.Transaction;
import com.example.account.service.AccountService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka Consumer — account-service consumer group.
 *
 * Patterns demonstrated:
 *  • Notification Pattern     : logs the event
 *  • Event-Carried State Transfer : updates the account balance from the payload
 *  • Idempotency              : delegated to AccountService
 *
 * Consumer group "account-service" means all instances of this service
 * share the load across partitions (competing consumers).
 */
@Component
public class KafkaTransactionConsumer {

    private final AccountService accountService;

    public KafkaTransactionConsumer(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaListener(topics = "transactions", groupId = "account-service")
    public void consume(String message) {
        // ── Notification Pattern ──────────────────────────────────────
        System.out.println("[Kafka] Received event: " + message);

        // ── Event-Carried State Transfer ──────────────────────────────
        // Parse the toString() representation produced by the producer.
        // In production, use JSON deserialization (see RabbitMQ consumer).
        Transaction tx = parseFromString(message);
        if (tx != null) {
            String result = accountService.apply(tx);
            System.out.println("[Kafka] " + result);
        }
    }

    /**
     * Parses "Transaction{id=1, userId=101, amount=500.0, type=DEBIT}"
     * produced by Transaction.toString() in the producer.
     */
    private Transaction parseFromString(String message) {
        try {
            String content = message.replaceAll("Transaction\\{", "").replace("}", "");
            String[] parts = content.split(", ");
            Long   id     = Long.parseLong(parts[0].split("=")[1]);
            Long   userId = Long.parseLong(parts[1].split("=")[1]);
            double amount = Double.parseDouble(parts[2].split("=")[1]);
            String type   = parts[3].split("=")[1];
            return new Transaction(id, userId, amount, type);
        } catch (Exception e) {
            System.err.println("[Kafka] Could not parse message: " + message);
            return null;
        }
    }
}
