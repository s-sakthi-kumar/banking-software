package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(String order) {

        System.out.println("Received: " + order);
    }
}
