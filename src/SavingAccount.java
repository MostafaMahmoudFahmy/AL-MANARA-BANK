public class SavingAccount extends Account {

    private double annualInterestRate;
    private int monthlyWithdrawalCount;


    public SavingAccount(String accountNumber, double balance, Customer owner, AccountStatus status, double annualInterestRate) {
        super(accountNumber, balance, owner, status);
        this.annualInterestRate = annualInterestRate;
        monthlyWithdrawalCount = 0;
    }


    @Override
    public void setBalance(double balance) {
        if (balance > 0)
            super.setBalance(balance);
        else
            System.out.println("Balance Must Be Postive");
    }


    @Override
    public boolean withdraw(double amount) {
        if (getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Rejected Ya Pro :) , Active Your Account ");
            System.out.println("Your Account Status Is : " + getStatus());
            return false;
        }
        if (amount > getBalance()) {
            System.out.println("Insufficient balance  :) ");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Amount Must Be Positive ");
            return false;

        }
        if (amount < MIN_TRANSACTION_AMOUNT) {
            System.out.println("Minimum Transaction Is :  " + MIN_TRANSACTION_AMOUNT);
            return false;
        }

        setBalance(getBalance() - amount);
        IncrementTransactionCount();
        monthlyWithdrawalCount++;

        System.out.println(" Withdrawal successful .");
        System.out.println("New Balance Is : " + getBalance());

        return true;

    }


    @Override
    public String getAccountType() {
        return "Savings";
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public double calculateAnnualInterest() {
        return getBalance() * (annualInterestRate / 100);
    }

    public void applyAnnualInterest() {

        double interest = calculateAnnualInterest();

        setBalance(getBalance() + interest);

        System.out.println("Interest Added: $" + interest);
        System.out.println("New Balance: $" + getBalance());
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public int getMonthlyWithdrawalCount() {
        return monthlyWithdrawalCount;
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("Annual Interest Rate : " + getAnnualInterestRate());
        System.out.println("Monthly Withdrawal : " + getMonthlyWithdrawalCount());
    }
}
