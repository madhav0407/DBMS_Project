import java.sql.*;
import java.text.SimpleDateFormat;
// import java.util.Date;

public class DebitCardDAO_JDBC implements DebitCardDAO {
    Connection dbConnection;

    public DebitCardDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    private int getNumberOfCards() {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select count(*) from debitCard;";
        int val = -1;
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                val = resultSet.getInt(1);
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
        return val;
    }

    public DebitCard addCard(DebitCard db) {
        PreparedStatement preparedStatement = null;
        String sql;
        sql = "insert into debitCard VALUES (?, ?, ?, ?, ?);";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, db.getAccountNum());

            int numOfCards = this.getNumberOfCards();
            String cardNum = Integer.toString(numOfCards);
            int len = cardNum.length();
            String temp = "";
            for (int i = 0; i < 16 - len; i++) {
                temp += "0";
            }
            cardNum = temp + cardNum;
            preparedStatement.setString(2, cardNum);
            try {
                java.util.Date date1 = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(db.getExpDate());
                preparedStatement.setDate(3, new java.sql.Date(date1.getTime()));
            } catch (java.text.ParseException e) {
                // Handle the exception here
                e.printStackTrace();
            }
            preparedStatement.setInt(4, numOfCards);
            preparedStatement.setString(5, db.getName());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();
            db.setCardNum(cardNum);
            db.setCVV(numOfCards);
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
        return db;
    }

    public DebitCard getCard(String cardNum) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from debitCard where cardNum = ?;";
        DebitCard db = new DebitCard();
        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, cardNum);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                db.setAccntNum(resultSet.getString(1));
                db.setCardNum(cardNum);

                java.sql.Date date = resultSet.getDate(3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateString = dateFormat.format(date);
                db.setExpDate(dateString);

                db.setCVV(resultSet.getInt(4));
                db.setName(resultSet.getString(5));
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
        return db;
    }

    public Transaction withdraw(DebitCard db, float amount, TransactionDAO tdao) {
        PreparedStatement preparedStatement = null;
        String sql;
        Transaction tra = new Transaction();
        sql = "update account set balance = balance - ? where accountNumber = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(2, db.getAccountNum());
            preparedStatement.setFloat(1, amount);

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) {
                return tra;
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
        Transaction trans = new Transaction(amount, db.getAccountNum(), "NULL", "withdraw", "card");
        trans = tdao.addTransaction(trans);
        return trans;
    }

    public Transaction transfer(DebitCard db, Account acc2, float amount, TransactionDAO tdao) {
        PreparedStatement preparedStatement1 = null;
        String sql1;
        sql1 = "update account set balance = balance - ? where accountNumber = ?;";

        PreparedStatement preparedStatement2 = null;
        String sql2;
        sql2 = "update account set balance = balance + ? where accountNumber = ?;";
        Transaction tra = new Transaction();

        try {

            preparedStatement1 = dbConnection.prepareStatement(sql1);
            preparedStatement2 = dbConnection.prepareStatement(sql2);

            preparedStatement1.setFloat(1, amount);
            preparedStatement1.setString(2, db.getAccountNum());

            preparedStatement2.setFloat(1, amount);
            preparedStatement2.setString(2, acc2.getAccountNum());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();

            Transaction trans = new Transaction(amount, db.getAccountNum(), acc2.getAccountNum(), "transfer", "card");
            trans = tdao.addTransaction(trans);
            return trans;
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

        try {
            if (preparedStatement2 != null) {
                preparedStatement2.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tra;
    }
}