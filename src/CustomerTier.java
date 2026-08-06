public enum CustomerTier {
    STANDARD(20 , 0),
    SILVER(10 , 1),
    GOLD(5 , 3);

    private final double monthlyFee;
    private final double interestBonus;
    CustomerTier(double monthlyFee , double interestBonus){
        this.monthlyFee = monthlyFee;
        this.interestBonus = interestBonus;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public double getInterestBonus() {
        return interestBonus;
    }

    @Override
    public String toString() {
        return name();
    }
}
