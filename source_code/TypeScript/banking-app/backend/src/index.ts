import express from "express";

import type { Account } from "./models/account.js";
import {Service} from "./services/accountService.js"
import accountRoutes from "./routes/accountRoutes";




const account: Account ={
  id:1234,
  name:"sk",
  address:"earth",
  balance:500
};

const service = new Service(account);
  
console.log(account);
console.log(service);
console.log(service.deposit(1000));
console.log(service.withdraw(900));



const app = express();

app.use(express.json());

app.use("/accounts", accountRoutes);

app.listen(3000, () => {
  console.log("Server running on port 3000");
});