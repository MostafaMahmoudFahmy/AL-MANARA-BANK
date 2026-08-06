public class FixedDepositAccount extends Account {
    private double interestRate;
    private int durartionInMonth;
    private int monthsPassed;

    public FixedDepositAccount(String accountNumber, double balance, Customer owner, AccountStatus status, double interestRate, int durartionInMonth , int monthsPassed) {
        super(accountNumber, balance, owner, status);
        this.interestRate = interestRate;
        this.durartionInMonth = durartionInMonth;
        this.monthsPassed = monthsPassed;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getDurartionInMonth() {
        return durartionInMonth;
    }

    public void setDurartionInMonth(int durartionInMonth) {
        this.durartionInMonth = durartionInMonth;
    }

    public int getMonthsPassed() {
        return monthsPassed;
    }

    public void setMonthsPassed(int monthsPassed) {
        this.monthsPassed = monthsPassed;
    }


    public double calculateAnnualInterest() {
        return getBalance() * (interestRate / 100);
    }

    public void applyAnnualInterest() {

        double interest = calculateAnnualInterest();

        setBalance(getBalance() + interest);

        System.out.println("Interest Added: $" + interest);
        System.out.println("New Balance: $" + getBalance());
    }



    public boolean Ismaturity() {
        return monthsPassed >= durartionInMonth;
    }


    @Override
    public boolean withdraw(double amount) {
        if (getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Your Account Is : " + getStatus());
            System.out.println("Active Your Account");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid Amount : Amount Must Be Positave");
            return false;
        }
        if (amount < MIN_TRANSACTION_AMOUNT) {
            System.out.println("The Min Transaction Amount is : " + MIN_TRANSACTION_AMOUNT);
            return false;
        }
        if (!Ismaturity()) {
            System.out.println("Withdrawal rejected, The Account Has Not Matured Yet ");
            System.out.println("Months Remaining: " + (durartionInMonth - monthsPassed));
            return false;
        }
        if (getBalance() - amount < 0) {
            System.out.println("Insufficient Balance ");
            return false;
        }

        setBalance(getBalance() - amount);
        IncrementTransactionCount();

        System.out.println("Withdrawal successful.");
        System.out.println("New Balance: " + getBalance());

        return true;
    }

    @Override
    public String getAccountType() {
        return "Fixed Deposit Account";
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("Interest Rate    : " + interestRate);
        System.out.println("Duration In Month: " + getDurartionInMonth() + " months");
        System.out.println("Months Passed    : " + getMonthsPassed());
        System.out.println("Matured          : " + (Ismaturity() ? "Yes" : "No"));
    }
}
