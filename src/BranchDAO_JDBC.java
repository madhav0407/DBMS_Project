import java.sql.*;
import java.util.ArrayList;
public class BranchDAO_JDBC implements BranchDAO {
    Connection dbConnection;

    public BranchDAO_JDBC (Connection dbconn) {
        dbConnection = dbconn;
    }
    public ArrayList<Branch> getBranches () {
        PreparedStatement preparedStatement = null;
        String sql;
        ArrayList<Branch> branches = new ArrayList<Branch>();
        sql = "select * from branch;";
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Branch branch = new Branch(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                        resultSet.getString(4));
                branches.add(branch);    
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
        return branches; 
    }
}
