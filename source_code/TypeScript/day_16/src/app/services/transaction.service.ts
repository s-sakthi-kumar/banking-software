import { Injectable } from '@angular/core';
import { Transaction } from '../models/transaction';

@Injectable({
  providedIn:'root'
})
export class TransactionService {


  addTransaction(transaction: Transaction){

    const transactions: Transaction[] =
      JSON.parse(
        localStorage.getItem('transactions') || '[]'
      );


    transactions.push(transaction);


    localStorage.setItem(
      'transactions',
      JSON.stringify(transactions)
    );

  }



  getTransactions(userId:number){

    const transactions: Transaction[] =
      JSON.parse(
        localStorage.getItem('transactions') || '[]'
      );


    return transactions.filter(
      t => t.userId === userId
    );

  }


}
