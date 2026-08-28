package com.example.rabbitmq.controller;

import com.example.rabbitmq.producer.OrderProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String createOrder(@RequestBody String order) {

        producer.sendOrder(order);

        return "Order sent";
    }
}
