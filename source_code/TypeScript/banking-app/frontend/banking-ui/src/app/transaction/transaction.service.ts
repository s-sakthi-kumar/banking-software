import { Injectable } from '@angular/core';
import { CurrencyPipe, formatCurrency } from '@angular/common';
@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private balance = 5000;
  private dict = {};

  getBalance(): number {
    return this.balance;
  }

  deposit(amount: number): string {
    const currencyPipe = new CurrencyPipe('en-IN');
    if (amount <= 0) {
      return 'Enter a valid amount.';
    }

    this.balance += amount;
    return `${currencyPipe.transform(amount,'INR')} deposited successfully.`;
  }

  withdraw(amount: number): string {
    const currencyPipe = new CurrencyPipe('en-IN');
  if (amount <= 0) {
      return 'Enter a valid amount.';
    }

    if (amount > this.balance) {
      return 'Insufficient balance.';
    }

    this.balance -= amount;
    return `${currencyPipe.transform(amount,'INR')} withdrawn successfully.`;
  }
}
