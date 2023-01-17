package bankProject;

import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    /*
     * first name of the user
     */
    private String firstName;
    /*
     * last name of the user
     */
    private String lastName;
    /*
     * universal identififier
     */
    private String uuid;
    /*
     * md5 hash algorithm
     */
    private byte pinHash[];

    private ArrayList<Account> accounts;

    /**
     * Creates a new user
     * 
     * @param first   users first name
     * @param second  user last name
     * @param pin     user's account pin number
     * @param theBank bank that the user is customer of
     */

    User(String first, String second, String pin, Bank theBank) {
        // set users name
        this.firstName = first;
        this.lastName = second;

        // store the md5(message digest) pin hash rather than the pin
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        } catch (Exception e) {
            System.out.println("No such algorithm exists, pebete");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique uuid
        this.uuid = theBank.getNewUserUUID();
        // create empty user list
        this.accounts = new ArrayList<Account>();
        // print user creation
        System.out.printf("New user %s, %s, with ID %s created", this.firstName,
                this.lastName, this.uuid);
    }

    /**
     * add account to the accounts list
     * 
     * @param newAccount account to add to the array list
     */

    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }

    /**
     * @return User uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Digest received pin and test it against this.hashPin
     * 
     * @param pin
     * @return
     */
    public boolean validatePin(String pin) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(this.pinHash, md.digest(pin.getBytes()));
        } catch (Exception e) {
            System.out.println("Couldn't instanciate this class");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getName(){
        return this.firstName;
    }

    public void printAccountsSummary(){
        Account acc;
        double accountBalance;
        System.out.printf("%s %s's accounts summary: \n", this.firstName, this.lastName);
        for (int i = 0; i < this.accounts.size(); i++) {
            acc = this.accounts.get(i);
            accountBalance = acc.getBalance();
            if(accountBalance >= 0){
                System.out.printf("    %d) %s : %s : $ %.2f \n",i+1, acc.getUUID(),acc.getName(),accountBalance);
            }else{
                System.out.printf("    %d) %s : $ ( %.2f ) \n",i+1, acc.getUUID(),accountBalance);
            }
        }
    }

    public Account getAccount(int accountIndex){
        if(accountIndex >= 0 && accountIndex < this.accounts.size()){
            return this.accounts.get(accountIndex);
        }
        return null;
    }
}
