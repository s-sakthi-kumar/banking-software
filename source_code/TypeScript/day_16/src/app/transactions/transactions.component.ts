import { Component } from '@angular/core';
import { TransactionService } from '../services/transaction.service';
import { AuthService } from '../services/auth.service';
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';

@Component({
selector:'app-transactions',
templateUrl:'./transactions.component.html',
imports:[CommonModule,DatePipe]
})
export class TransactionsComponent {


transactions:any[]=[];


constructor(
private transactionService:TransactionService,
private auth:AuthService
){}



ngOnInit(){

const user=this.auth.getUser();


this.transactions =
this.transactionService.getTransactions(
 user.id
);


}



addMoney(){

const user=this.auth.getUser();


this.transactionService.addTransaction({

id:Date.now(),

userId:user.id,

type:'credit',

amount:1000,

description:'Salary',

date:new Date().toISOString()

});


this.ngOnInit();

}


}
