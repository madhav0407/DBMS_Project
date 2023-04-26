import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class TransactionDAO_JDBC implements TransactionDAO {
	Connection dbConnection;

	public TransactionDAO_JDBC(Connection dbconn) {
		dbConnection = dbconn;
	}

	public Transaction addTransaction(Transaction trans) {
		PreparedStatement preparedStatement = null;
		String sql;
		Statement stmt = null;
		sql = "insert into transaction(amountTransferred, debitedFromAcc, creditedToAcc, transactionDate, transactionType, paymentMtd) VALUES (?, ?, ?, ?, ?, ?);";

		try {
			preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setFloat(1, trans.getAmountTransferred());

			if (trans.getDebitedAcc().equals("NULL")) {
				preparedStatement.setNull(2, Types.NULL);
			} else {
				preparedStatement.setString(2, trans.getDebitedAcc());
			}

			if (trans.getCreditedAcc().equals("NULL")) {
				preparedStatement.setNull(3, Types.NULL);
			} else {
				preparedStatement.setString(3, trans.getCreditedAcc());
			}

			try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(trans.getTransactionDate());
				preparedStatement.setDate(4, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}
			preparedStatement.setString(5, trans.getTransactionType());
			preparedStatement.setString(6, trans.getPaymentMtd());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM transaction");
			int tid = -1;
			while (rs.next()) {
				tid = rs.getInt(1);
			}
			trans.setTransactionID(tid);
			return trans;
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
		return trans;
	}

	public ArrayList<Transaction> getTransactions(Account acc, String startDate, String endDate) {
		PreparedStatement preparedStatement = null;
		String sql;
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		sql = "select * from transaction where (debitedFromAcc = ? OR creditedToAcc = ?) AND (transactionDate BETWEEN DATE(?) AND DATE(?));";
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, acc.getAccountNum());
			preparedStatement.setString(2, acc.getAccountNum());
			try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(startDate);
				preparedStatement.setDate(3, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}
			try {
				java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(endDate);
				preparedStatement.setDate(4, new java.sql.Date(date1.getTime()));
			} catch (java.text.ParseException e) {
				// Handle the exception here
				e.printStackTrace();
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Transaction trans = new Transaction(resultSet.getFloat(2), resultSet.getString(3),
						resultSet.getString(4),
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