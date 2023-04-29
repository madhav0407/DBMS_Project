import java.sql.*;

public class LoginDAO_JDBC implements LoginDAO {
    Connection dbConnection;

    public LoginDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public Customer customerLogin(int customerID, String pass, CustomerDAO cdao) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from customerlogin l WHERE l.customerID = ? and l.pass = ?;";
        Customer customer = new Customer();

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setInt(1, customerID);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = cdao.getCustomer(customerID);
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
        return customer;
    }

    public Customer customerSignUp(String name, String phoneNumber, String address, String dob, String pass,
    CustomerDAO custDAO) {
        PreparedStatement preparedStatement = null;
        String sql;
        // Statement stmt = null;

        Customer customer = new Customer(name, phoneNumber, address, dob);
        customer = custDAO.addCustomer(customer);
        if (customer.getCustomerID() == -1) {
            return customer;
        }

        // try {
        // stmt = dbConnection.createStatement();
        // ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM customer");
        // int cid = -1;
        // while (rs.next()) {
        // cid = rs.getInt(1);
        // }

        sql = "insert into customerlogin(pass, customerID) VALUES (?, ?);";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, pass);
            preparedStatement.setInt(2, customer.getCustomerID());

            preparedStatement.executeUpdate();
            // customer.setCustomerID(customer.getCustomerID());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // } catch (SQLException e) {
        // System.out.println(e.getMessage());

        // }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(customer.getCustomerID());

        return customer;
    }

    public Admin adminLogin(int adminID, String pass, AdminDAO adminDAO) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from adminlogin l WHERE l.adminID = ? and l.pass = ?;";
        Admin admin = new Admin();

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setInt(1, adminID);
            preparedStatement.setString(2, pass);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = adminDAO.getAdmin(adminID);
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

    public Admin adminSignUp(String name, String pass, AdminDAO adminDAO) {
        PreparedStatement preparedStatement = null;
        String sql;
        Statement stmt = null;

        Admin admin = new Admin(name);
        adminDAO.addAdmin(admin);

        try {
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM admin");
            int aid = -1;
            while (rs.next()) {
                aid = rs.getInt(1);
            }

            sql = "insert into adminlogin(pass, adminID) VALUES (?, ?);";

            try {
                preparedStatement = dbConnection.prepareStatement(sql);

                preparedStatement.setString(1, pass);
                preparedStatement.setInt(2, aid);

                preparedStatement.executeUpdate();
                admin.setAdminID(aid);
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

        return admin;
    }

}
