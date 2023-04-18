public class Transaction {
    private int transactionID;
    private float amountTransferred;
    private String debitedFromAccount;
    private String creditedToAccount;
    private String transactionDate;
    private String transactionType;
    private String paymentMethod; 

    public Transaction (int transactionID, float amountTransferred, String debitedFromAccount, String creditedToAccount, String transactionDate, 
    String transactionType, String paymentMethod) {
        this.transactionID = transactionID;
        this.amountTransferred = amountTransferred;
        this.debitedFromAccount = debitedFromAccount;
        this.creditedToAccount = creditedToAccount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.paymentMethod = paymentMethod;                         
    }
    
    public int getTransactionID() { return transactionID; }
    public float getAmountTransferred() { return amountTransferred; }
    public String getDebitedAcc() { return debitedFromAccount; }
    public String getCreditedAcc() { return creditedToAccount; }
    public String getTransactionDate() { return transactionDate; }
    public String getTransactionType() { return transactionType; }
    public String getPaymentMtd() { return paymentMethod; }
}
