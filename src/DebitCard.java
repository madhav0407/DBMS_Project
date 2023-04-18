import javax.print.attribute.standard.PrinterInfo;

public class DebitCard {
    private String cardNumber;
    private String expDate;
    private int cvv;
    private String name;
    private String accountNumber;

    public String getCardNum() {
        return cardNumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getName() {
        return name;
    }

    public String getAccountNum() {
        return accountNumber;
    }

    public int getCVV() {
        return cvv;
    }

    public void setCardNum(String cardnum) {
        cardNumber = cardnum;
    }

    public void setExpDate(String expdate) {
        expDate = expdate;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setAccntNum(String accntnum) {
        accountNumber = accntnum;
    }

    public void setCVV(int CVV) {
        cvv = CVV;
    }

    public DebitCard() {
    }

    public DebitCard(String cardNumber, String expDate, int cvv, String name, String accountNumber) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cvv = cvv;
        this.name = name;
        this.accountNumber = accountNumber;
    }
}
