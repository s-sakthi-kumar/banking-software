package com.ecom;

/**
 * CardService
 */
public interface CardService {
    // here we will implement like constructor to initiate connection with bank 
    // or payment gateway
    // or somewhere else idk
    public ReturnCode performPayment(CardDetails card, double amount);
}