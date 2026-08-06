public abstract class Account {

    private String accountNumber;
    private double balance;
    private Customer owner;
    private AccountStatus status;
    private int successfulTransactionCount;

    public static final double MIN_TRANSACTION_AMOUNT = 10;


    public Account(String accountNumber, double balance, Customer owner, AccountStatus status) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
        this.status = AccountStatus.ACTIVE;
        successfulTransactionCount = 0;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public int getSuccessfulTransactionCount() {
        return successfulTransactionCount;
    }

    public void setSuccessfulTransactionCount(int successfulTransactionCount) {
        this.successfulTransactionCount = successfulTransactionCount;
    }

    public void IncrementTransactionCount() {
        successfulTransactionCount++;
    }

    public abstract boolean withdraw(double amount);

    public abstract String getAccountType();




    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Amount Must Be Postive :(");
            return false;
        }

        if (status != AccountStatus.ACTIVE) {
            System.out.println("Your Account Status Is : " + getStatus());
            System.out.println("The Account Must Be Active :( , please Active Your Account ");
            return false;
        }

        if (MIN_TRANSACTION_AMOUNT >= amount) {
            System.out.println("The Min Amount Is :  " + MIN_TRANSACTION_AMOUNT);
            return false;
        }
//        double totalAmount = getBalance() - amount;
        setBalance(getBalance() + amount);
        IncrementTransactionCount();

        return true;

    }

    public void displayAccountDetails() {

        System.out.println("----------------------------------");
        System.out.println("Account Number : " + getAccountNumber());
        System.out.println("Owner          : " + owner.getFullName());
        System.out.println("Type           : " + getAccountType());
        System.out.println("Balance        : $" + getBalance());
        System.out.println("Status         : " + status);
        System.out.println("Transactions   : " + getSuccessfulTransactionCount());
    }


}
