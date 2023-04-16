public class Login {
    private int customerID;
    private String password;

    public Login(int cid, String pass) {
        customerID = cid;
        password = pass;
    }
    public Login() {
        customerID = -1;
        password = "-1";
    }
}