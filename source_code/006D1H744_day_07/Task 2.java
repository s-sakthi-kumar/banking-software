class BankAccount{
 
    String accountNumber;
    double balance;

    BankAccount(String accountNumber,double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    double deposit(double amount){
        if(amount<0){
            throw new IllegalArgumentException("Negative numbers not allowed");
        }
        this.balance = this.balance+amount;
        return this.balance;
    }

    double withdraw(double amount){
        if(amount<0){
            throw new IllegalArgumentException("Negative numbers not allowed");
        }
        if(amount<balance){
            throw new IllegalArgumentException("Insufficient balance");
        }
        return this.balance;
    }
}
