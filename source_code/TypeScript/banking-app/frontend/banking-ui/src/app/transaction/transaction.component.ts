import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [FormsModule],
  template: `
    <h2>Transaction</h2>

    <p>Balance: ₹{{ balance }}</p>

    <input
      type="number"
      [(ngModel)]="amount"
      placeholder="Enter amount"
    />

    <br><br>

    <button (click)="deposit()">Deposit</button>
    <button (click)="withdraw()">Withdraw</button>
    @for (item of message; track item) {
    <li>{{ item }}</li>
    }
  `
})
export class TransactionComponent {

  amount = 0;
  balance = 0;
  message = [''];

  constructor(private transactionService: TransactionService) {
    this.balance = this.transactionService.getBalance();
  }

  deposit() {
    this.message.push(this.transactionService.deposit(this.amount));
    this.balance = this.transactionService.getBalance();
    this.amount = 0;
  }

  withdraw() {
    this.message.push(this.transactionService.withdraw(this.amount));
    this.balance = this.transactionService.getBalance();
    this.amount = 0;
  }
}
