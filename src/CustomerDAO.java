public interface CustomerDAO {
    public void addCustomer(Customer cust);
    public Account createAccount(int customerID, float balance, float minBalance, int branchID, AccountDAO adao); // AccountDAO to be passed in function
    public Boolean deleteAccount(Account acc, AccountDAO adao);
    public Boolean accountLogin(String accountNum);
}