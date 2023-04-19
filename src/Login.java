public class Login {
    private int customerID;
    private String password;

    public String getPassword() {
        return password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void setCustomerID(int cid) {
        customerID = cid;
    }

    public Login(int cid, String pass) {
        customerID = cid;
        password = pass;
    }

    public Login() {
        customerID = -1;
        password = "-1";
    }
}