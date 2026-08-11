import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { authGuard } from './guards/auth.guard';


export const routes:Routes=[

{
 path:'login',
 component:LoginComponent
},

{
 path:'transactions',
 component:TransactionsComponent,
 canActivate:[authGuard]
}

];
