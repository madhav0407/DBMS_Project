import java.util.ArrayList;
import java.sql.*;

public class AccountDAO_JDBC implements AccountDAO {
    Connection dbConnection;

    public AccountDAO_JDBC(Connection dbconn) {
        dbConnection = dbconn;
    }

    public Account getAccount(String accNum) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select * from account a where a.accountNumber = ? and a.accountStatus = 1;";
        Account acc = new Account();
        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, accNum);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                acc.setCustomerID(resultSet.getInt(1));
                acc.setAccountNumber(resultSet.getString(2));
                acc.setBalance(resultSet.getDouble(3));
                acc.setMinBalance(resultSet.getFloat(4));
                acc.setBranchID(resultSet.getInt(5));
                acc.setAccountStatus(resultSet.getInt(6));
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
        return acc;
    } // for creating an account object for transfer and all

    public float getBalance(Account acc) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select balance from account a where a.accountNumber = ?;";
        float balance = -1;
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getAccountNum());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getFloat(1);
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
        return balance;
    }

    public boolean deleteAccount(Account acc) {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "update account set accountStatus = 0 where accountNumber = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, acc.getAccountNum());

            int affected = preparedStatement.executeUpdate();
            if (affected == 0) {
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

    private int getNumberOfAccounts() {
        PreparedStatement preparedStatement = null;
        String sql;

        sql = "select count(*) from account;";
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

    public Account addAccount(int customerID, float balance, float minBalance, int branchID) {
        PreparedStatement preparedStatement = null;
        String sql;
        Account acc = new Account();

        sql = "insert into account(customerID, accountNumber, balance, minBalance, branchID, accountStatus) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setFloat(3, balance);
            preparedStatement.setFloat(4, minBalance);
            preparedStatement.setInt(5, branchID);
            preparedStatement.setInt(6, 1);

            int numOfAcc = this.getNumberOfAccounts();
            String accountNum = Integer.toString(numOfAcc);
            int len = accountNum.length();
            String temp = "";
            for (int i = 0; i < 8 - len; i++) {
                temp += "0";
            }
            accountNum = temp + accountNum;
            preparedStatement.setString(2, accountNum);
            preparedStatement.executeUpdate();
            acc.setAccountNumber(accountNum);
            acc.setAccountStatus(1);
            acc.setBalance(balance);
            acc.setBranchID(branchID);
            acc.setCustomerID(customerID);
            acc.setMinBalance(minBalance);
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
        return acc;
    }

    public Transaction withdraw(Account acc, float amount, TransactionDAO tdao) {
        PreparedStatement preparedStatement = null;
        String sql;
        Transaction tra = new Transaction();
        sql = "update account set balance = balance - ? where accountNumber = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(2, acc.getAccountNum());
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
        Transaction trans = new Transaction(amount, acc.getAccountNum(), "NULL", "withdraw", "account");
        trans = tdao.addTransaction(trans);
        return trans;
    }

    public Transaction deposit(Account acc, float amount, TransactionDAO tdao) {
        PreparedStatement preparedStatement = null;
        String sql;
        Transaction tra = new Transaction();
        sql = "update account set balance = balance + ? where accountNumber = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(2, acc.getAccountNum());
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
        Transaction trans = new Transaction(amount, "NULL", acc.getAccountNum(), "deposit", "cash");
        trans = tdao.addTransaction(trans);
        return trans;
    }

    public Transaction transfer(Account acc1, Account acc2, float amount, TransactionDAO tdao) {
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
            preparedStatement1.setString(2, acc1.getAccountNum());

            preparedStatement2.setFloat(1, amount);
            preparedStatement2.setString(2, acc2.getAccountNum());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();

            Transaction trans = new Transaction(amount, acc1.getAccountNum(), acc2.getAccountNum(), "transfer",
                    "account");
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

    public ArrayList<Transaction> getTransactions(Account acc, String startDate, String endDate, TransactionDAO tdao) {
        ArrayList<Transaction> transactions = tdao.getTransactions(acc, startDate, endDate);
        return transactions;
    }

    public DebitCard addCard(DebitCardDAO ddao, Account acc, String nameOnCard) {
        DebitCard debitCard = new DebitCard();
        debitCard.setAccntNum(acc.getAccountNum());
        debitCard.setName(nameOnCard);
        debitCard = ddao.addCard(debitCard);
        return debitCard;
    }

    public float getSpending(Account acc) {
        PreparedStatement preparedStatement = null;
        String sql;
        float amount = -1;
        int flag = 0;
        sql = "select transactionID, amountTransferred, debitedFromAcc, creditedToAcc from transaction where creditedToAcc = ? OR debitedFromAcc = ?;";
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getAccountNum());
            preparedStatement.setString(2, acc.getAccountNum());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false){ // Done to check if no transactions made by account
                amount = 0;
            } else {
                do {
                    if (flag == 0) {
                        flag = 1;
                        amount = 0;
                    }
                    if (acc.getAccountNum().equals(resultSet.getString(3))) {
                        amount -= resultSet.getFloat(2);
                    } else {
                        amount += resultSet.getFloat(2);
                    }
                } while(resultSet.next());
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
        return amount;
    }
}