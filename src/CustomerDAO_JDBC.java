import java.sql.*;

public class CustomerDAO_JDBC implements CustomerDAO {
    Connection dbConnection;
    public CustomerDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public Boolean accountLogin(String accountNum) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from account a where a.accountNumber = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, accountNum);

            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {} else {
                return false;
                // Login failed
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
        return true;
    }    

    public Boolean deleteAccount (Account account, AccountDAO adao) {
        Boolean ans = adao.deleteAccount(account);
        return ans;
    }
    public void addCustomer (Customer cust) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "insert into customer(c_name, phone_num, c_address, dob) VALUES (?, ?, ?, ?);";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, cust.getName());
            preparedStatement.setString(2, cust.getPhonenum());
            preparedStatement.setString(3, cust.getAddress());
            try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(cust.getDOB());
				preparedStatement.setDate(4, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}
            
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
    }
    public Account createAccount (int customerID, float balance, float minBalance, int branchID, AccountDAO adao) {
        Account acc = new Account();
        acc = adao.addAccount(customerID, balance, minBalance, branchID);
        return acc;
    }
}