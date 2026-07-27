package com.ecom;
import org.springframework.stereotype.Service;

@Service
public class OrderService{
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    void orderProduct(User user, Product product, CardDetails cardDetails, Integer qty){
        paymentService.makePayment( (double)qty*product.amount, user, cardDetails);
        System.out.println("ordered: "+qty+"x : "+product.name+"for "+user.name);
    }
}