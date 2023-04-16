import java.util.ArrayList;
public interface AccountDAO {
    public Account getAccount (String accNum); // for creating an account object for transfer and all
    public float getBalance (Account acc);
    public boolean addAccount (Account acc);
    public boolean deleteAccount (Account acc);
    public boolean withdraw (Account acc, float amount);
    public boolean deposit (Account acc, float amount);
    public boolean transfer (Account acc1, Account acc2, float amount);
    public ArrayList<Transaction> getTransactions (Account acc, String startDate, String endDate);
    // log out?
}
