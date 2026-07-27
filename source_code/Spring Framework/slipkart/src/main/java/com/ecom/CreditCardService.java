package com.ecom;
import org.springframework.stereotype.Service;


@Service
public class CreditCardService implements CardService {
    @Override
    public ReturnCode performPayment(CardDetails card, double amount){
        // payment gateway use card for this payment
        System.out.println("payment made with Credit card"+card.cardNumber+"for "+amount);
        return ReturnCode.SUCCESS;
    }
}