import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DebitCardDAO_JDBC implements DebitCardDAO {
    Connection dbConnection;

    public DebitCardDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public void addCard(DebitCard card) {
        PreparedStatement preparedStatement = null;
        String sql;
        sql = "insert into debitCard VALUES (?, ?, ?, ?, ?);";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, card.getAccountNum());
            preparedStatement.setString(2, card.getCardNum());

            try {
                java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(card.getExpDate());
                preparedStatement.setDate(3, new java.sql.Date(date1.getTime()));
            } catch (java.text.ParseException e) {
                // Handle the exception here
                e.printStackTrace();
            }
            preparedStatement.setInt(4, card.getCVV());
            preparedStatement.setString(5, card.getName());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Debit Card no. " + card.getCardNum()
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

    public void withdraw(DebitCard card, float amount) {

        PreparedStatement preparedStatement1 = null;
        String sql1;
        sql1 = "update account set balance = balance - ? where accountNumber = ?;";
        DAO_Factory dao_Factory = new DAO_Factory();

        try {
            preparedStatement1 = dbConnection.prepareStatement(sql1);

            preparedStatement1.setFloat(1, amount);
            preparedStatement1.setString(2, card.getAccountNum());

            // execute insert SQL stetement
            preparedStatement1.executeUpdate();

            // Getting the current date.
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String formattedDate = formatter.format(date);

            Transaction trans = new Transaction(1, amount, card.getAccountNum(), "NULL", formattedDate, "Withdraw",
                    "card");

            try {
                TransactionDAO tdao = dao_Factory.getTransactionDAO();
                tdao.addTransaction(trans);
            } catch (Exception e) {

            }

            System.out.println("Withdraw Succesfull");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (preparedStatement1 != null) {
                preparedStatement1.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
