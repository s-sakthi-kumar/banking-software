package com.example.transaction.controller;

import com.example.transaction.config.RabbitConfig;
import com.example.transaction.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes two endpoints:
 *
 *  POST /transaction        → publishes to Kafka topic "transactions"
 *  POST /transaction/rabbit → publishes to RabbitMQ topic exchange
 *
 * Both demonstrate Event-Carried State Transfer: the full transaction
 * payload is embedded in the event so consumers need no callback.
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;

    public TransactionController(KafkaTemplate<String, String> kafkaTemplate,
                                 RabbitTemplate rabbitTemplate) {
        this.kafkaTemplate  = kafkaTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Kafka producer — partitioned by userId so all events for a user
     * land on the same partition (ordering guarantee).
     */
    @PostMapping
    public String createTransaction(@RequestBody Transaction tx) {
        kafkaTemplate.send("transactions", String.valueOf(tx.getUserId()), tx.toString());
        System.out.println("[Kafka] Published: " + tx);
        return "Transaction submitted via Kafka: " + tx.getId();
    }

    /**
     * RabbitMQ producer — uses topic exchange with routing key so
     * multiple queues can subscribe to different transaction patterns.
     */
    @PostMapping("/rabbit")
    public String createTransactionRabbit(@RequestBody Transaction tx) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, tx);
        System.out.println("[RabbitMQ] Published: " + tx);
        return "Transaction submitted via RabbitMQ: " + tx.getId();
    }
}
