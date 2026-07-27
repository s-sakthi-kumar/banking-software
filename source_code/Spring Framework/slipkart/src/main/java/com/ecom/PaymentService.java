package com.ecom;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PaymentService{
    private final CardService cardService;

    public PaymentService(CardService cardService){
        this.cardService = cardService;
    }

    public void makePayment(double amount, User userDetails, CardDetails cardDetails){
        ReturnCode rtnCode = cardService.performPayment(cardDetails, amount);
        System.out.println("Order: return code"+ rtnCode.getMessage());
    }
}

