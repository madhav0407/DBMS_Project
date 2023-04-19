public interface LoginDAO {
    public Boolean customerLogin (int customerID, String pass);
    public int signUp (String name, String phoneNumber, String address, String dob, String pass, CustomerDAO custDAO);
}
