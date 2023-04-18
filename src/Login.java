public class Login {
    private int customerID;
    private String password;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void setCustomerID(int cid) {
        customerID = cid;
    }

    public Login(int cid, String pass, String name) {
        customerID = cid;
        password = pass;
        username = name;
    }

    public Login() {
        customerID = -1;
        password = "-1";
        username = "-1";
    }
}