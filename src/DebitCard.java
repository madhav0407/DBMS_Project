import javax.print.attribute.standard.PrinterInfo;

public class DebitCard {
    private String cardNumber;
    private String expDate;
    private int cvv;
    private String name;
    private String accountNumber;

    public DebitCard (String cardNumber, String expDate, int cvv, String name, String accountNumber) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cvv = cvv;
        this.name = name;
        this.accountNumber = accountNumber;
    }
}
