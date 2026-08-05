import type { Account } from "../models/account.js";

export class Service{
  account:Account; 
  constructor(account:Account){
    this.account = account;
  }

  withdraw(amount:number):number{
    if (amount<=this.account.balance){
      this.account.balance-=amount;
      return this.account.balance;
    }
    return this.account.balance;
  }
  deposit(amount:number):number{
    this.account.balance+= amount;
    return this.account.balance;
  }
}
