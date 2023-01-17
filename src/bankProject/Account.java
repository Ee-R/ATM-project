package bankProject;

import java.util.ArrayList;

public class Account {
    /*
     * universal account identifier
     */
    private String uuid;
    /*
     * balance of the account
     */
    private String name;
    /*
     * account's owner
     */
    private User owner;
    /*
     * transaction list
     */
    ArrayList<Transaction> transactions;

    private double balance;

    /**
     * Create new account instance
     * 
     * @param name    account's name
     * @param holder  owner's pointer
     * @param theBank bank who owns the account
     */
    Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.owner = holder;
        // set uuid
        this.uuid = theBank.getNewAccountUUID();

        // initialize the transactions
        this.transactions = new ArrayList<Transaction>();
    }
    /* get uuid
     * @return uuid
     */
    String getUUID() {
        return this.uuid;
    }
    /**
     * gets name account
     * @return name account
     */
    public String getName() {
        return this.name;
    }
    /**
     * gets balance
     * @return actual balance
     */
    public double getBalance(){
        return this.balance;
    }
    /**
     * Makes transaction
     * @param amount amount to withdraw
     * @param memo description of the transaction
     * @return double actual balance
     */
    public double withdraw(double amount, String memo){
        Transaction newTransaction = new Transaction((-1)*amount,this,memo);
        this.balance = this.balance - amount;
        this.transactions.add(newTransaction);
        return this.balance;
    }
    
    /**
     * Makes a transaction to deposit on the account
     * @param amount amount to deposit
     * @param memo description of the transaction
     * @return the balance left after the transaction
     */
    public double deposit(double amount, String memo){
        Transaction newTransaction = new Transaction(amount,this,memo);
        this.balance = this.balance + amount;
        this.transactions.add(newTransaction);
        return this.balance;
    }
    /**
     * Builds a string to return it
     * @return a string with the transaction history
     */
    public String transactionsHistory(){
        String history = "";
        for(Transaction transaction : this.transactions){
            history += transaction.getSummary();
            history += "-------------------------\n";
        }
        return history;
    }


    
}
