import java.sql.*;

/*
	Methods to be called in the following order:

	1. activateConnection
	2. 	Any number getDAO calls with any number of database transactions
	3. deactivateConnection
*/
public class DAO_Factory {

	public enum TXN_STATUS {
		COMMIT, ROLLBACK
	};

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Bank?characterEncoding=latin1&useConfigs=maxPerformance";
	static final String USER = "root";
	static final String PASS = "shlok";
	Connection dbconnection = null;

	// You can add additional DAOs here as needed
	TransactionDAO transactionDAO = null;
	LoginDAO loginDAO = null;
	CustomerDAO customerDAO = null;
	AccountDAO accountDAO = null;
	DebitCardDAO debitCardDAO = null;
	AdminDAO adminDAO = null;
	BranchDAO branchDAO = null;

	boolean activeConnection = false;

	public DAO_Factory() {
		dbconnection = null;
		activeConnection = false;
	}

	public void activateConnection() throws Exception {
		if (activeConnection == true)
			throw new Exception("Connection already active");

		System.out.println("Connecting to database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbconnection = DriverManager.getConnection(DB_URL, USER, PASS);
			dbconnection.setAutoCommit(false); // This is useful when you want to execute multiple SQL statements as
												// part of a
			// single transaction, and you want to ensure that either all the statements are
			// committed or none of them are committed.
			activeConnection = true;
		} catch (ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public TransactionDAO getTransactionDAO() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (transactionDAO == null)
			transactionDAO = new TransactionDAO_JDBC(dbconnection);

		return transactionDAO;
	}

	public LoginDAO getLoginDao() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (loginDAO == null)
			loginDAO = new LoginDAO_JDBC(dbconnection);

		return loginDAO;
	}

	public CustomerDAO getCustomerDao() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (customerDAO == null)
			customerDAO = new CustomerDAO_JDBC(dbconnection);

		return customerDAO;
	}

	public AccountDAO getAccountDao() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (accountDAO == null)
			accountDAO = new AccountDAO_JDBC(dbconnection);

		return accountDAO;
	}

	public DebitCardDAO getDebitCardDAO() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (debitCardDAO == null)
			debitCardDAO = new DebitCardDAO_JDBC(dbconnection);

		return debitCardDAO;
	}

	public AdminDAO getAdminDAO() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (adminDAO == null)
			adminDAO = new AdminDAO_JDBC(dbconnection);

		return adminDAO;
	}
	public BranchDAO getBranchDAO() throws Exception {
		if (activeConnection == false)
			throw new Exception("Connection not activated...");

		if (branchDAO == null)
			branchDAO = new BranchDAO_JDBC(dbconnection);

		return branchDAO;
	}

	public void deactivateConnection(TXN_STATUS txn_status) {
		// Okay to keep deactivating an already deactivated connection
		activeConnection = false;
		if (dbconnection != null) {
			try {
				if (txn_status == TXN_STATUS.COMMIT)
					dbconnection.commit();
				else
					dbconnection.rollback();

				dbconnection.close();
				dbconnection = null;

				// Nullify all DAO objects
				// studentDAO = null;
				transactionDAO = null;
				loginDAO = null;
				customerDAO = null;
				accountDAO = null;
				debitCardDAO = null;
				adminDAO = null;	
				branchDAO = null;
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
	}
};