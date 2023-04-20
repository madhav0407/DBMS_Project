import java.util.ArrayList;
public interface TransactionDAO {
    public Transaction addTransaction (Transaction trans);
    public ArrayList<Transaction> getTransactions (Account acc, String startDate, String endDate);
}