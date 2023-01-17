package bankProject;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Transaction {
    /*
     * transaction amount
     */
    private double amount;
    /*
     * transaction time stamp
     */
    private Date timeStamp;
    /*
     * optional memo, text zone
     */
    private String memo;
    /*
     * account made the transaction
     */
    private Account inAccount;

    /**
     * Create a new transaction
     * 
     * @param amount    amount transacted
     * @param inAccount account from where we are making the transaction
     */
    Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    /**
     * Overloaded constructor
     * 
     * @param amount
     * @param memo
     * @param inAccount
     */

    Transaction(double amount, Account inAccount, String memo) {
        this(amount, inAccount);
        this.memo += memo;
    }

    String getSummary(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newString = String.format(
                "Date : %s\n"+
                "AccountID : %s" +
                "Amount : %.2f \n" +
                "Description : %s \n",
                df.format(this.timeStamp),
                this.inAccount.getName(),
                this.amount,
                this.memo);

        return newString;
    }
}
