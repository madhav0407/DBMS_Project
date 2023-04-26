import java.util.ArrayList;

public interface CustomerDAO {
    public Customer getCustomer(int customerID);

    public Customer addCustomer(Customer cust);

    // AccountDAO to be passed in createAccount() function
    public Account createAccount(int customerID, float balance, float minBalance, int branchID, AccountDAO adao);

    public Boolean deleteAccount(Account acc, AccountDAO adao);

    public Account accountLogin(String accountNum, AccountDAO adao);

    public ArrayList<Transaction> getTransactions(Customer customer);
}