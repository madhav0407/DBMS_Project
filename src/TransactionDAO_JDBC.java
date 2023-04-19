import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TransactionDAO_JDBC implements TransactionDAO {
	Connection dbConnection;

	public TransactionDAO_JDBC(Connection dbconn) {
		dbConnection = dbconn;
	}

	public void addTransaction(Transaction trans) {
		PreparedStatement preparedStatement = null;
		String sql;
		sql = "insert into transaction VALUES (?, ?, ?, ?, ?, ?, ?);";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setInt(1, trans.getTransactionID());
			preparedStatement.setFloat(2, trans.getAmountTransferred());

			if (trans.getDebitedAcc().equals("NULL")){
				preparedStatement.setNull(3, Types.NULL);
			}
			else{
				preparedStatement.setString(3, trans.getDebitedAcc());
			}

			if (trans.getCreditedAcc().equals("NULL")){
				preparedStatement.setNull(4, Types.NULL);
			}
			else{
				preparedStatement.setString(4, trans.getCreditedAcc());
			}

			try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(trans.getTransactionDate());
				preparedStatement.setDate(5, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}
			preparedStatement.setString(6, trans.getTransactionType());
			preparedStatement.setString(7, trans.getPaymentMtd());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Transaction no. " + trans.getTransactionID()
					+ ", added to the database");

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
}
