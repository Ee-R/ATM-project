package bankProject;

import java.util.Scanner;

public class ATM {
    private static final int SHOW_TRANSACTIONS = 1;
    private static final int WITHDRAW = 2;
    private static final int DEPOSIT = 3;
    private static final int TRANSFER = 4;
    private static final int QUIT = 5;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank("Macro");
        // add user to the bank which also create savings account
        User userA = bank.addUser("Elias","Rojas","1234");

        Account newAccount = new Account("checking",userA,bank);
        userA.addAccount(newAccount);

        User mainUser;
        while (true) {
            // stay in the login until successful login
            mainUser = mainMenuPrompt(bank,sc);

            // stay in main menu until user quits
            printUserMenu(mainUser,sc);

        }
    }

    private static User mainMenuPrompt(Bank theBank,Scanner sc){
        String userID;
        String pin;
        User authUser;
        // prompt the user id/pin combo until get it right
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());

            System.out.println("Enter user ID : ");
            userID = sc.nextLine();
            System.out.println("Enter user pin : ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null) {
                System.out.println("Invalid credentials");
            }
        } while (authUser == null);
        // continue once we authenticate user

        return authUser;
    }

    private static void printUserMenu(User user,Scanner sc){
        int choice;
        // print summary of the user account

        do {
            user.printAccountsSummary();
            System.out.printf("Welcome %s, what would you like to do?\n",user.getName());
            System.out.println(
                    "    1) Show transaction history\n" + 
                    "    2) Withdraw\n" +
                    "    3) Deposit\n" + 
                    "    4) Transfer\n" + 
                    "    5) Quit\n"
                    );

            System.out.println("Input your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case SHOW_TRANSACTIONS:
                    showTransactionHistory(user,sc);
                    break;
                case WITHDRAW:
                    withdraw(user,sc);
                    break;
                case DEPOSIT:
                    deposit(user,sc);
                    break;
                case TRANSFER:
                    transfer(user,sc);
                    break;
                default:
                    if(choice == QUIT){
                        System.out.println("Bye bye\n");
                    }else{
                        System.out.println("Invalid input\n");
                    }
                    break;
            }
        } while (choice != QUIT);
    }

    public static void showTransactionHistory(User user, Scanner sc){
        int accountIndex;
        Account userAccount;

        do {
            System.out.println("Input the index of the account: ");
            accountIndex = sc.nextInt();
            userAccount = user.getAccount(accountIndex-1);
            if(userAccount == null){
                System.out.println("Invalid account. Please try again.");
            }
        } while (userAccount == null);

        System.out.println(userAccount.transactionsHistory());

    }
    public static void transfer(User user, Scanner sc) {
        int ixSender; // index of the sender account
        int ixReceiver; // index of the receiver account
        Account sender;
        Account receiver;
        double amount;
        double balance;
        String memo;
                        
        do {
            System.out.println("Input the index of the account to send from : ");
            ixSender = sc.nextInt();
            sender = user.getAccount(ixSender - 1);
            if(sender == null){
                System.out.println("Account is not valid, try again");
            }
        } while (sender == null);

        System.out.println(
                "The balance available is : " +
                String.format("%.2f", balance = sender.getBalance())
                );
        
        do {
            System.out.println("Input the index of the account to send the amount : ");
            ixReceiver = sc.nextInt();
            receiver = user.getAccount(ixReceiver - 1);
            if(receiver == null){
                System.out.println("Account is not valid, try again");
            }
        } while (receiver == null);

        do {
            System.out.println("Insert amount to send : ");
            amount = sc.nextDouble();
            if(balance < amount){
                // if balance < amount, balance would be left negative
                System.out.println("Not enough balance, try again");
            }
        } while (balance < amount);

        System.out.println("Insert an optional description of the transaction: ");
        memo = sc.nextLine();


        sender.withdraw(amount,memo);
        receiver.deposit(amount,memo);

        System.out.println(String.format(
                    "Transaction summary:\n"+
                    "Sender : %s\n" +
                    "Receiver : %s \n" +
                    "Amount : $ %.2f \n", sender.getUUID(), receiver.getUUID(), amount));

        System.out.println("Operation succeeded");
    }

    public static void withdraw(User user, Scanner sc) {
        double balance;
        double amount;
        Account accountSelected;
        String memo;

        do {
            System.out.println("Input the index of the account to withdraw from : ");
            accountSelected = user.getAccount(sc.nextInt() - 1);
            if(accountSelected == null){
                System.out.println("Account is not valid, try again");
            }
        } while (accountSelected == null);

        balance = accountSelected.getBalance();

        do {
            System.out.println("Insert amount to withdraw : ");
            amount = sc.nextDouble();
            if(balance < amount){
                // if balance < amount, balance would be left negative
                System.out.println("Not enough balance, try again");
            }
        } while (balance < amount);

        System.out.println("Insert an optional memo: ");
        memo = sc.nextLine();
        accountSelected.withdraw(amount,memo);

        System.out.printf("Wait for the money (%.2f)\n", amount);
        System.out.println("Operation succeeded");
    }

    public static void deposit(User user, Scanner sc) {
        double amount;
        Account accountSelected;
        String memo;

        do {
            System.out.println("Input the index of the account to deposit: ");
            accountSelected = user.getAccount(sc.nextInt() - 1);
            if(accountSelected == null){
                System.out.println("Account is not valid, try again");
            }
        } while (accountSelected == null);

        do {
            System.out.println("Insert amount to deposit : ");
            amount = sc.nextDouble();
            if(amount < 0){
                // if balance < amount, balance would be left negative
                System.out.println("Can not deposit negative money");
            }
        } while (amount < 0);

        System.out.println("Insert an optional memo: ");
        memo = sc.nextLine();
        accountSelected.deposit(amount,memo);
        System.out.println("Operation succeeded");
    }

}
