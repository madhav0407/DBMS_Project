import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import java.sql.*;


public class TransactionDAO_JDBC implements TransactionDAO {
	Connection dbConnection;
	public TransactionDAO_JDBC(Connection dbconn){
		dbConnection = dbconn;
	}

	public void addTransaction (Transaction trans) {
		PreparedStatement preparedStatement = null;																																																																																																																																													
		String sql;
		sql = "insert into transaction VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);
 
			preparedStatement.setInt(1, trans.getTransactionID());
			preparedStatement.setFloat(2, trans.getAmountTransferred());
			preparedStatement.setString(3, trans.getDebitedAcc());
			preparedStatement.setString(4, trans.getCreditedAcc());
			//java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(trans.getTransactionDate());
			try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(trans.getTransactionDate());
				preparedStatement.setDate(5, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}
			preparedStatement.setString(6, trans.getTransactionType());
			preparedStatement.setString(7, trans.getPaymentMtd());
			// preparedStatement.setInt(1, student.getRollno());
			// preparedStatement.setString(2, student.getName());
 
			// // execute insert SQL stetement
			// preparedStatement.executeUpdate();
 
			// System.out.println("Student: Roll No " + student.getRollno() 
			// 	+ ", added to the database");
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}

		try{
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
 			System.out.println(e.getMessage());
 		}
	}
    public Transaction getTransaction (String accountNum) {

	}
}
