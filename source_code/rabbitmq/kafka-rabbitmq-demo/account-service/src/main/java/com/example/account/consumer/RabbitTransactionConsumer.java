package com.example.account.consumer;

import com.example.account.model.Transaction;
import com.example.account.service.AccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ Consumer — listens on "transaction-queue".
 *
 * The Jackson2JsonMessageConverter declared in RabbitConfig automatically
 * deserializes the JSON body into a Transaction object (Event-Carried State Transfer).
 *
 * Idempotency is handled inside AccountService.
 */
@Component
public class RabbitTransactionConsumer {

    private final AccountService accountService;

    public RabbitTransactionConsumer(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = "${rabbitmq.queue:transaction-queue}")
    public void receive(Transaction tx) {
        // ── Notification Pattern ──────────────────────────────────────
        System.out.println("[RabbitMQ] Received event: " + tx);

        // ── Event-Carried State Transfer + Idempotency ────────────────
        String result = accountService.apply(tx);
        System.out.println("[RabbitMQ] " + result);
    }
}
