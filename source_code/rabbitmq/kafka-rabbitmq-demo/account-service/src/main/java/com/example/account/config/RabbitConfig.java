package com.example.account.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declares the same RabbitMQ topology as transaction-service.
 * Both services must agree on the exchange / queue / routing-key names.
 */
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE    = "transaction-exchange";
    public static final String QUEUE       = "transaction-queue";
    public static final String ROUTING_KEY = "transaction.key";

    @Bean
    public TopicExchange transactionExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding transactionBinding(Queue transactionQueue,
                                      TopicExchange transactionExchange) {
        return BindingBuilder
                .bind(transactionQueue)
                .to(transactionExchange)
                .with(ROUTING_KEY);
    }

    /**
     * Jackson converter so @RabbitListener receives a deserialized
     * Transaction object instead of raw bytes.
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
