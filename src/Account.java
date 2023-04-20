public class Account {
    private String accountNum;
    private float balance;
    private float minBalance;
    private int branchID;
    private int customerID;
    private int accountStatus; 

    public String getAccountNum(){
        return accountNum;
    }
    public float getBalance(){
        return balance;
    }
    public float getMinBalance(){
        return minBalance;
    }
    public int getBranchID(){
        return branchID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public int getAccountStatus(){
        return accountStatus;
    }
    
    public Account (String accountNum, float balance, float minBalance, int branchID, int customerID, int accountStatus) {
        this.accountNum = accountNum;
        this.balance = balance;
        this.minBalance = minBalance;
        this.branchID = branchID;
        this.customerID = customerID;
        this.accountStatus = accountStatus;
    } 
    public Account () {} 
    public void setCustomerID (int cid) {
        customerID = cid;
    }
    public void setAccountNumber (String acn) {
        accountNum = acn;
    }
    public void setBalance (float bal) {
        balance = bal;
    }
    public void setMinBalance (float mb) {
        minBalance = mb;
    }
    public void setBranchID (int bid) {
        branchID = bid;
    }
    public void setAccountStatus (int st) {
        accountStatus = st;
    }
}