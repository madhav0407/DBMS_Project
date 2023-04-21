import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

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

    public ArrayList<Transaction> getTransactions (Customer customer) {
        PreparedStatement preparedStatement = null;
        String sql;
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        sql = "select DISTINCT transactionID, amountTransferred, debitedFromAcc, creditedToAcc, transactionDate, transactionType, paymentMtd from account a inner join transaction t on t.debitedFromAcc = a.accountNumber OR t.creditedToAcc = a.accountNumber where a.customerID = ? order by transactionID;";
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getCustomerID());
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
				Transaction trans = new Transaction(resultSet.getFloat(2), resultSet.getString(3), resultSet.getString(4),
				resultSet.getString(6), resultSet.getString(7));
				trans.setTransactionID(resultSet.getInt(1));

				java.sql.Date date = resultSet.getDate(5);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String dateString = dateFormat.format(date);
				trans.setTransactionDate(dateString);
				transactions.add(trans);
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
        return transactions;
    }
}
