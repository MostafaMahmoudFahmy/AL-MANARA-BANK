public class Customer {

    private String fullName;
    private String NationalId;
    private String phoneNumber;
    private String CustomerID ;
    private CustomerTier customerTier;
    private int accountCount ;

    public Customer(String fullName, String nationalId, String phoneNumber, String customerID, CustomerTier customerTier) {
        this.fullName = fullName;
        NationalId = nationalId;
        this.phoneNumber = phoneNumber;
        CustomerID = customerID;
        this.customerTier = customerTier;
        this.accountCount = 0;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return NationalId;
    }

    public void setNationalId(String nationalId) {
        NationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public CustomerTier getCustomerTier() {
        return customerTier;
    }

    public void setCustomerTier(CustomerTier customerTier) {
        this.customerTier = customerTier;
    }

    public void incrementAccountCount() {
        accountCount++;
    }
    public void decrementAccountCount() {
        if (accountCount > 0) {
            accountCount--;
        }
    }

    public void displayCustomer() {
        System.out.println("Customer ID   : " + getCustomerID());
        System.out.println("Name          : " + getFullName());
        System.out.println("National ID   : " + getNationalId());
        System.out.println("Phone Number  : " + getPhoneNumber());
        System.out.println("Tier          : " + getCustomerTier());
//        System.out.println("Open Accounts : " + accountCount);
    }
}
