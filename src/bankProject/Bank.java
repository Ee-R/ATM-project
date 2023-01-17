package bankProject;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    /*
     * bank name
     */
    private String name;
    /*
     * bank users list
     */
    private ArrayList<User> users;
    /*
     * bank accounts list
     */
    private ArrayList<Account> accounts;

    /**
     * Create bank instance, start lists and add a name to it
     * 
     * @param bankName name of the bank
     */

    Bank(String bankName) {
        this.name = bankName;
        this.accounts = new ArrayList<Account>();
        this.users = new ArrayList<User>();
    }

    /**
     * Generate a user UUID
     * 
     * @return string length 6
     */
    public String getNewUserUUID() {
        // inits
        String newUUID;
        int len = 6;
        Random rand = new Random();
        boolean unique;

        // continue looping until getting an unique id
        do {
            newUUID = "";
            for (int i = 0; i < len; i++) {
                newUUID += (char) (rand.nextInt(10) + (int) '0');
                // generates a number between [0,9), adds the code of '0' to it and
                // cast to character
            }
            // check whether the id is unique or not
            unique = true;
            for (User user : this.users) {
                if (newUUID.equals(user.getUUID())) {
                    unique = false;
                    break;
                }
            }

        } while (!unique);
        return newUUID;
    }

    /**
     * Generate account uuid
     * 
     * @return string of length 6
     */
    public String getNewAccountUUID() {
        // inits
        String newUUID;
        int len = 10;
        Random rand = new Random();
        boolean unique;

        // continue looping until getting an unique id
        do {
            newUUID = "";
            for (int i = 0; i < len; i++) {
                newUUID += (char) (rand.nextInt(10) + (int) '0');
                // generates a number between [0,9), adds the code of '0' to it and
                // cast to character
            }
            // check whether the id is unique or not
            unique = true;
            for (Account account : this.accounts) {
                if (newUUID.equals(account.getUUID())) {
                    unique = false;
                    break;
                }
            }

        } while (!unique);
        return newUUID;
    }

    /**
     * Add account to the array list
     * 
     * @param newAccount
     */
    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }

    /**
     * Add user to the users list
     * 
     * @param firstName
     * @param lastName
     * @param pin
     */
    public User addUser(String firstName, String lastName, String pin) {
        // create and add user to the list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create an account for the recently created user

        Account newAccount = new Account("savings", newUser, this);

        // point the bank and the user the recent account
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        // search through the list of users
        for (User user : this.users) {
            // if I find the user
            if (user.getUUID().equals(userID) && user.validatePin(pin)) {
                return user;
            }
        }
        // if user's not found, return null
        return null;
    }

    /**
     * get bank name
     * 
     * @return String, bank name
     */
    public String getName() {
        return this.name;
    }


}
