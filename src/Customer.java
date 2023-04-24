import java.util.*;

public class Customer {
    private int customerID;
    private String name;
    private String phoneNum;
    private String address;
    private String dob; // may turn into date data type

    public String getName() {
        return name;
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public String getDOB() {
        return dob;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setPhonenum(String phNum) {
        phoneNum = phNum;
    }

    public void setAddress(String Address) {
        address = Address;
    }

    public void setDOB(String DOB) {
        dob = DOB;
    }

    public void setCustomerID(int cid) {
        customerID = cid;
    }

    public Customer() {
        customerID = -1;
    }

    public Customer (String Name, String phNum, String Address, String DOB) {
        customerID = -1;
        name = Name;
        phoneNum = phNum;
        address = Address;
        dob = DOB;
    }
}
