public class CurrentAccount extends Account {
    private double overDraft;

    public CurrentAccount(String accountNumber, double balance, Customer owner, AccountStatus status, double overDraft) {
        super(accountNumber, balance, owner, status);
        this.overDraft = overDraft;
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    public boolean IsUsingOverDraft() {
        return getBalance() < 0;
    }

    @Override
    public boolean withdraw(double amount) {
        if (getStatus() != AccountStatus.ACTIVE) {
            System.out.println("Your Account Status IS : " + getStatus());
            System.out.println("Active Your Account");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Invalid Amount ");
            return false;
        }
        if (amount < MIN_TRANSACTION_AMOUNT) {
            System.out.println("Rejected , the minimum Transaction Amount is : " + MIN_TRANSACTION_AMOUNT);
            return false;
        }
        if (getBalance() - amount < -getOverDraft()) {
            System.out.println("Rejected , the amount is more than overdraft limit");
            return false;
        }

        setBalance(getBalance() - amount);
        IncrementTransactionCount();

        System.out.println("Withdrawal successful :) ");


        if (IsUsingOverDraft()) {
            System.out.println("Account Is Using the Over Draft");
        }

        return true;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public void displayAccountDetails() {
        super.displayAccountDetails();
        System.out.println("The over draft limit is " + getOverDraft());
        System.out.println("this account "+(IsUsingOverDraft() ? "Use" : "Not use") + " The Over Draft Limit");
    }
}
