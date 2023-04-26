import java.util.ArrayList;

public interface AdminDAO {
    public Admin getAdmin(int adminID);

    public Admin addAdmin(Admin admin);

    public ArrayList<Account> getAccounts(Admin admin, AccountDAO adao);
}
