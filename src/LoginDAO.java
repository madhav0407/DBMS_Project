public interface LoginDAO {
    public Customer customerLogin (int customerID, String pass, CustomerDAO cdao);
    public Customer customerSignUp (String name, String phoneNumber, String address, String dob, String pass, CustomerDAO custDAO);
    public Admin adminLogin (int adminID, String pass, AdminDAO adminDAO);
    public Admin adminSignUp (String name, String pass, AdminDAO adminDAO);
}