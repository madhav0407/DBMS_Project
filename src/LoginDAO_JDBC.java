import java.sql.*;

public class LoginDAO_JDBC implements LoginDAO {
    Connection dbConnection;
    public LoginDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public Boolean customerLogin(int customerID, String pass) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from login l WHERE l.customerID = ? and l.pass = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, pass);
            
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

    public int signUp (String name, String phoneNumber, String address, String dob, String pass, CustomerDAO custDAO) {
        PreparedStatement preparedStatement = null;
        String sql;
        Statement stmt = null;
    
        Customer customer = new Customer(name, phoneNumber, address, dob);
        custDAO.addCustomer(customer);

        // sql = "insert into customer(c_name, phone_num, c_address, dob) VALUES (?, ?, ?, ?);";

        // try {
        //     preparedStatement = dbConnection.prepareStatement(sql);

        //     preparedStatement.setString(1, name);
        //     preparedStatement.setString(2, phoneNumber);
        //     preparedStatement.setString(3, address);
        //     try {
		// 		java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(dob);
		// 		preparedStatement.setDate(4, new java.sql.Date(date1.getTime()));
		// 	} catch (java.text.ParseException e) {
		// 		// Handle the exception here
		// 		e.printStackTrace();
		// 	}
            
        //     preparedStatement.executeUpdate();
        // } catch (SQLException e) {
        //     System.out.println(e.getMessage());
        // }

        
        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM customer");
            int cid = -1;
            while (rs.next()) {
                cid = rs.getInt(1);
            }

            sql = "insert into login(pass, customerID) VALUES (?, ?);";

            try {
                preparedStatement = dbConnection.prepareStatement(sql);
                
                preparedStatement.setString(1, pass);
                preparedStatement.setInt(2, cid);
                
                preparedStatement.executeUpdate();
                return cid;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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

        return 0;
    }
}
