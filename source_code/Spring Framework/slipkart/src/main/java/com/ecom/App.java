package com.ecom;
// package com.ecom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
   public static void main(String[] args) {
       // Load Spring configuration from applicationContext.xml
       ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");


       User someUser = new User("John","123");
       Product someProduct = new Product("SampleItem", 99);
       
        CardDetails newCard = new CardDetails();
        newCard.cardNumber = "123412341234";

        OrderService orderService = context.getBean(OrderService.class);

        orderService.orderProduct(
                someUser,
                someProduct,
                newCard,
                2
        );
       // Perform a demo transfer
       
   }
}
