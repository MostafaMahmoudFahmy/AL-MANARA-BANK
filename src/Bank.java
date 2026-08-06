import java.util.Scanner;

public class Bank {

    private static final int MAX_CUSTOMERS = 100;
    private static final int MAX_ACCOUNTS = 200;


    public static final double SAVINGS_MIN_BALANCE = 100;
    public static final double CURRENT_MIN_BALANCE = 500;
    public static final double FIXED_MIN_BALANCE = 1000;


    private Customer[] customers;
    private Account[] accounts;

    private int customerCounter;
    private int accountCounter;

    private int nextCustomerId;
    private int nextAccountNumber;

    private Scanner scanner;

    public Bank() {
        customers = new Customer[MAX_CUSTOMERS];
        accounts = new Account[MAX_ACCOUNTS];
        customerCounter = 0;
        accountCounter = 0;

        nextCustomerId = 1;
        nextAccountNumber = 1;
        scanner = new Scanner(System.in);

    }

    // Generate Customer Id
    public String GenerateCustomerID() {
        return String.valueOf(nextCustomerId++);
    }

    //Generate Account ID
    public String GenerateAccountID() {
        // return "A"+String.valueOf(nextAccountNumber++);
        return "A" + nextAccountNumber++;
    }

    //find Customer

    public Customer getCustomerById(String id) {
        for (int i = 0; i < customerCounter; i++) {
            if (customers[i].getCustomerID().equalsIgnoreCase(id))
                return customers[i];
        }
        return null;
    }

    // find account using account number

    public Account getAccountById(String accountNumber) {
        for (int i = 0; i < accountCounter; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accountNumber))
                return accounts[i];
        }
        return null;
    }

    public boolean nationalIdCheck(String nationalId) {

        for (int i = 0; i < customerCounter; i++) {

            if (customers[i].getNationalId().equals(nationalId)) {
                return true;
            }

        }

        return false;
    }

    // check phone validation
    public boolean phoneValidation(String phone) {
        if (phone.isEmpty()) {
            System.out.println("Phone Number Is Empty");
            return false;
        }
        if (phone.length() < 7 || phone.length() > 15) {
            System.out.println("Invalid Phone Number (7 - 15)");
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Check Customer Full Or Not
    public boolean customerIsFull() {
        return customerCounter >= MAX_CUSTOMERS;
    }

    public boolean accountsIsFull() {
        return accountCounter >= MAX_ACCOUNTS;
    }

    // check input validations
    public int readInt(String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextInt()) {

                int value = scanner.nextInt();
                scanner.nextLine();

                return value;
            }

            System.out.println("Invalid input.");
            scanner.nextLine();
        }
    }

    public double readDouble(String message) {
        while (true) {
            System.out.println(message);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                return value;
            }
            System.out.println("Invalid Input , Enter A double Number");
            scanner.nextLine();
        }
    }

    public String readString(String message) {

        System.out.print(message);
        return scanner.nextLine();
    }

    // Register A New Customer

    public void registerCustomer() {

        if (customerIsFull()) {
            System.out.println("Customer storage is full.");
            return;
        }

        System.out.println("********* Register New Customer *********");

        String fullName = readString("Enter Full Name: ");

        if (fullName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String nationalId = readString("Enter National ID: ");

        if (nationalIdCheck(nationalId)) {
            System.out.println("National ID already exists.");
            return;
        }

        String phone = readString("Enter Phone Number : ");

        if (!phoneValidation(phone)) {
            System.out.println("Invalid phone number.");
            return;
        }

        System.out.println("\nChoose Customer Tier");
        System.out.println("1. STANDARD");
        System.out.println("2. SILVER");
        System.out.println("3. GOLD");

        int choice = readInt("Choice: ");

        CustomerTier tier;

        switch (choice) {

            case 1:
                tier = CustomerTier.STANDARD;
                break;

            case 2:
                tier = CustomerTier.SILVER;
                break;

            case 3:
                tier = CustomerTier.GOLD;
                break;

            default:
                System.out.println("Invalid Tier.");
                return;
        }

        String customerId = GenerateCustomerID();

        Customer customer = new Customer(fullName, nationalId, phone, customerId, tier);

        customers[customerCounter++] = customer;

        System.out.println("\nCustomer Registered Successfully.");
        customer.displayCustomer();
    }

    // Open A new Account
    public void openAccount() {
        if (accountsIsFull()) {
            System.out.println("Account Storage is full :)");
            return;
        }
        System.out.println("************ open new account ************");

        String customerId = readString("Enter Customer Id");
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer Not Found :(");
            return;
        }
        System.out.println("Choose Account Type");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.println("3. Fixed Deposit");

        int choice = readInt("Enter Your Account Type : ");
        double openingBalance = readDouble("Enter Opening Balance : ");
        Account account;

        switch (choice) {
            case 1:
                if (openingBalance < SAVINGS_MIN_BALANCE) {
                    System.out.println("Rejected ,Minimum Opening Balance : " + SAVINGS_MIN_BALANCE);
                    return;
                }
                double annualInterest = readDouble("Enter Interest Rate");
                account = new SavingAccount(GenerateAccountID(), openingBalance, customer, AccountStatus.ACTIVE, annualInterest);
                break;

            case 2:
                if (openingBalance < CURRENT_MIN_BALANCE) {
                    System.out.println("Minimum opening Current Balance Is :" + CURRENT_MIN_BALANCE);
                    return;
                }
                double overDraft = readDouble("Enter Over Draft :");
                account = new CurrentAccount(GenerateAccountID(), openingBalance, customer, AccountStatus.ACTIVE, overDraft);
                break;
            case 3:
                if (openingBalance < FIXED_MIN_BALANCE) {
                    System.out.println("Minimum opening Fixed Deposit Balance Is :" + FIXED_MIN_BALANCE);
                    return;
                }
                double interestRate = readDouble("Interest Rate: ");
                int duration = readInt("Duration (Months): ");
                int monthsPassed = readInt("months Passed : ");
                account = new FixedDepositAccount(GenerateAccountID(), openingBalance, customer, AccountStatus.ACTIVE, interestRate, duration, monthsPassed );
                break;
            default:
                System.out.println("Invalid Account Type.");
                return;
        }
        accounts[accountCounter++] = account;
        customer.incrementAccountCount();
        System.out.println("Account Opened Successfully.");
        account.displayAccountDetails();

    }

    // Deposite Money
    public void depositMoney() {
        System.out.println("******* Deposit Money *******");

        String accountNumber = readString("Enter Account Number: ");
        Account account = getAccountById(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        double amount = readDouble("Enter Your Amount : ");
        account.deposit(amount);
        System.out.println("Deposit Done :)");
        System.out.println("Your Balance : " + account.getBalance());
    }

    // withdraw Money
    public void withdrawMoney() {
        System.out.println("********* Withdraw Money *********");
     String accountNum = readString("Enter Acccount Number");
        Account account = getAccountById(accountNum);
        if (account == null) {
            System.out.println("Account Not Found");
            return;
        }

        double amount = readDouble("Enter Withdrawal Amount : ");
        account.withdraw(amount);
        System.out.println("Your Balance : "+ account.getBalance()) ;
    }

    public void transferMoney() {
        System.out.println("******** Transfer Between Accounts ********");
        System.out.println();
        System.out.println("Enter Source Account Number : ");
        String sourceNumber = scanner.nextLine();
        System.out.println("Enter Destination Account Number : ");
        String destinationNumber = scanner.nextLine();

        if (sourceNumber.equalsIgnoreCase(destinationNumber)) {
            System.out.println("Source and destination accounts must be different.");
            return;
        }

        Account source = getAccountById(sourceNumber);
        Account destination = getAccountById(destinationNumber);

        if (source == null) {
            System.out.println("Source account not found.");
            return;
        }
        if (destination == null) {
            System.out.println("Destination account not found.");
            return;
        }
        double amount = readDouble("Enter Amount");
        if (!source.withdraw(amount)) {
            System.out.println("Transfer failed.");
            return;
        }
        if (!destination.deposit(amount)) {
            source.deposit(amount);
            System.out.println("Transfer failed.");
            System.out.println("Money restored to source account.");

            return;

        }
        System.out.println("Transfer completed successfully.");
    }

    // display customer Accounts
    public void displayCustomerAccounts() {
        System.out.println("********* Display Customer Accounts *********");
        String customerId = readString("Enter Customer ID: ");

        Customer customer = getCustomerById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        customer.displayCustomer();

        double totalBalance = 0;
        boolean found = false;

        for (int i = 0; i < accountCounter; i++) {

            if (accounts[i].getOwner().getCustomerID().equals(customerId)) {

                accounts[i].displayAccountDetails();

                totalBalance += accounts[i].getBalance();

                found = true;
            }
        }

        if (!found) {
            System.out.println("This customer has no accounts.");
        }

        System.out.println("Combined Balance: $" + totalBalance);

    }

    public void displayAllAccounts() {

        System.out.println("********* All Branch Accounts *********");

        if (accountCounter == 0) {
            System.out.println("No accounts found.");
            return;
        }

        for (int i = 0; i < accountCounter; i++) {

            accounts[i].displayAccountDetails();

        }
    }

    public void searchAccountByNumber() {

        System.out.println("********* Search Account *********");

        String accountNumber = readString("Enter Account Number: ");

        if (accountNumber.isEmpty()) {
            System.out.println("Account number cannot be empty.");
            return;
        }

        Account account = getAccountById(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");
            return;
        }

        account.displayAccountDetails();
    }

    public void searchAccountsByType() {

        System.out.println("********* Search By Account Type *********");

        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.println("3. Fixed Deposit");

        int choice = readInt("Choice: ");

        String type;

        switch (choice) {

            case 1:
                type = "Savings";
                break;

            case 2:
                type = "Current";
                break;

            case 3:
                type = "Fixed Deposit";
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }

        int count = 0;
        double totalBalance = 0;

        for (int i = 0; i < accountCounter; i++) {

            if (accounts[i].getAccountType().equals(type)) {

                accounts[i].displayAccountDetails();

                count++;
                totalBalance += accounts[i].getBalance();

            }

        }

        System.out.println("Matching Accounts : " + count);
        System.out.println("Combined Balance : " + totalBalance);
    }

    // close account
    public void closeAccount() {

        System.out.println("********* Close Account *********");

        String accountNumber = readString("Enter Account Number: ");

        Account account = getAccountById(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");
            return;
        }

        if (account.getStatus() == AccountStatus.CLOSED) {

            System.out.println("Account is already closed.");
            return;
        }

        if (account.getBalance() != 0) {

            System.out.println("Account balance must be zero.");
            return;
        }

        if (account instanceof FixedDepositAccount) {

            FixedDepositAccount fd = (FixedDepositAccount) account;

            if (!fd.Ismaturity()) {

                System.out.println("Fixed Deposit has not matured.");
                return;
            }

        }

        account.setStatus(AccountStatus.CLOSED);

        account.getOwner().decrementAccountCount();

        System.out.println("Account closed successfully.");
    }


}
