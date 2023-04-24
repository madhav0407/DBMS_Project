import java.sql.*;
import java.util.ArrayList;

public class AdminDAO_JDBC {
    Connection dbConnection;
    public AdminDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public Admin getAdmin(int adminID) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from admin a where a.adminID = ?;";
        Admin admin = new Admin();

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setInt(1, adminID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin.setAdminID(adminID);
                admin.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }    

    public Admin addAdmin (Admin admin) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "insert into admin(name) VALUES (?);";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, admin.getName());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        PreparedStatement preparedStatement2 = null;
        String sql2;

        sql2 = "select count(*) from admin;";
        int val = -1;
        try {
            preparedStatement2 = dbConnection.prepareStatement(sql2);
            ResultSet resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                val = resultSet.getInt(1); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (preparedStatement2 != null) {
                preparedStatement2.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        admin.setAdminID(val);
        return admin;
    }

    public ArrayList<Account> getTransactions (Admin admin, AccountDAO adao){
        PreparedStatement preparedStatement = null;
        String sql;
		ArrayList<Account> accounts = new ArrayList<Account>();

        sql = "select accountNumber from account a, branch b where a.branchID = b.branchID and b.managerID = ?;";
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, admin.getAdminID());
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
				accounts.add(adao.getAccount(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}
