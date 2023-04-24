import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void setCVV (int CVV) {
        cvv = CVV;
    }

    public DebitCard() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        Date futureDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(futureDate);
        this.expDate = formattedDate;
    }

    public DebitCard (String cardNumber, String expDate, int cvv, String name, String accountNumber) {
        this.cardNumber = cardNumber;
        this.expDate = expDate;
        this.cvv = cvv;
        this.name = name;
        this.accountNumber = accountNumber;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        Date futureDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(futureDate);
        this.expDate = formattedDate;
    }
}
