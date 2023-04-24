import java.util.*;

public class Admin {

    private int adminID;
    private String name; // may turn into date data type

    public String getName() {
        return name;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setAdminID(int aid) {
        adminID = aid;
    }

    public Admin() {    
        adminID = -1;
    }

    public Admin (String Name) {
        adminID = -1;
        name = Name;
    }
}
