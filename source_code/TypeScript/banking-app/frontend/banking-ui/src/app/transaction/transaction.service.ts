import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private balance = 5000;

  getBalance(): number {
    return this.balance;
  }

  deposit(amount: number): string {
    if (amount <= 0) {
      return 'Enter a valid amount.';
    }

    this.balance += amount;
    return `₹${amount} deposited successfully.`;
  }

  withdraw(amount: number): string {
    if (amount <= 0) {
      return 'Enter a valid amount.';
    }

    if (amount > this.balance) {
      return 'Insufficient balance.';
    }

    this.balance -= amount;
    return `₹${amount} withdrawn successfully.`;
  }
}
