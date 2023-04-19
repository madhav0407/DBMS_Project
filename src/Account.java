import java.security.PublicKey;

public class Account {
    private String accountNum;
    private float balance;
    private float minBalance;
    private int branchID;
    private int customerID;
    private boolean accountStatus; 

    public String getAccountNum(){
        return accountNum;
    }
    
    public Account (String accountNum, float balance, float minBalance, int branchID, int customerID, boolean accountStatus) {
        this.accountNum = accountNum;
        this.balance = balance;
        this.minBalance = minBalance;
        this.branchID = branchID;
        this.customerID = customerID;
        this.accountStatus = accountStatus;
    } 
}

