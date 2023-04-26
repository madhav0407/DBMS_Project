public class Branch {
    private int branchID;
    private int managerID;
    private String branchLocation;
    private String branchContact;

    Branch (int branchID, int managerID, String branchLocation, String branchContact) {
        this.branchContact = branchContact;
        this.branchID = branchID;
        this.managerID = managerID;
        this.branchLocation = branchLocation;
    }
    Branch () {
        branchID = -1;
        managerID = -1;
    }
    public void printAll() {
        System.out.println("Branch ID: " + this.branchID);
        System.out.println("Branch Location: " + this.branchLocation);
        System.out.println("Branch Contact: " + this.branchContact);
    }
}
