import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private int transactionID;
    private float amountTransferred;
    private String debitedFromAccount;
    private String creditedToAccount;
    private String transactionDate;
    private String transactionType;
    private String paymentMethod; 

    public Transaction (float amountTransferred, String debitedFromAccount, String creditedToAccount,
    String transactionType, String paymentMethod) {
        transactionID = -1;
        this.amountTransferred = amountTransferred;
        this.debitedFromAccount = debitedFromAccount;
        this.creditedToAccount = creditedToAccount;
        this.transactionType = transactionType;
        this.paymentMethod = paymentMethod;                         
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String formattedDate = formatter.format(date);
        this.transactionDate = formattedDate;
    }
    public Transaction() {
        transactionID = -1;
    }
    
    public int getTransactionID() { return transactionID; }
    public float getAmountTransferred() { return amountTransferred; }
    public String getDebitedAcc() { return debitedFromAccount; }
    public String getCreditedAcc() { return creditedToAccount; }
    public String getTransactionDate() { return transactionDate; }
    public String getTransactionType() { return transactionType; }
    public String getPaymentMtd() { return paymentMethod; }
    public void setTransactionID (int tid) {
        transactionID = tid;
    }
    public void setTransactionDate (String dat) {
        transactionDate = dat;
    }
    
    public void printAll () {
        System.out.println("Transaction ID: " + this.transactionID);
        System.out.println("Amount Transferred: " + this.amountTransferred);
        System.out.println("Debited From Account: " + this.debitedFromAccount);
        System.out.println("Credited To Account: " + this.creditedToAccount);
        System.out.println("Transaction Type: " + this.transactionType);
        System.out.println("Transaction Date: " + this.transactionDate);
        System.out.println("Payment Method: " + this.paymentMethod);
    }
}
