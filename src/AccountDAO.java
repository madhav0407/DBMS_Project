import java.util.ArrayList;

public interface AccountDAO {
    public Account getAccount(String accNum); // for creating an account object for transfer and all

    public float getBalance(Account acc);

    public Account addAccount(int customerID, float balance, float minBalance, int branchID);

    public boolean deleteAccount(Account acc);

    public Transaction withdraw(Account acc, float amount, TransactionDAO tdao);

    public Transaction deposit(Account acc, float amount, TransactionDAO tdao);

    public Transaction transfer(Account acc1, Account acc2, float amount, TransactionDAO tdao);

    public ArrayList<Transaction> getTransactions(Account acc, String startDate, String endDate, TransactionDAO tdao);

    public DebitCard addCard(DebitCardDAO ddao, Account acc, String nameOnCard);

    public float getSpending(Account acc);
}