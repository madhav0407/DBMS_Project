public interface CustomerDAO {
    public void addCustomer(Customer cust);
    public void createAccount(float balance, float minBalance); // AccountDAO to be passed in function
    public Boolean deleteAccount(String accountNum);
    public Boolean accountLogin(String accountNum);
    
}