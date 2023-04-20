import java.util.ArrayList;
public class Test {
    public static DAO_Factory daoFactory;
    public static void main(String[] args) {
        try {
			daoFactory = new DAO_Factory();

			System.out.println();
			System.out.println("Running usecase1");
            try {
                // Start transaction boundary
                daoFactory.activateConnection();
    
                // Carry out DB operations using DAO
                LoginDAO ldao = daoFactory.getLoginDao();
                CustomerDAO cdao = daoFactory.getCustomerDao();
                TransactionDAO tdao = daoFactory.getTransactionDAO();
                AccountDAO adao = daoFactory.getAccountDao();
                DebitCardDAO ddao = daoFactory.getDebitCardDAO();

                Account acc = adao.getAccount("00000000");
                // System.out.println(acc.getAccountNum() + " " + acc.getAccountStatus() + " " + acc.getBalance() + " " + acc.getBranchID() + " " + acc.getCustomerID() + " " + acc.getMinBalance());
                // int val = ldao.signUp("sl", "1343512", "teri maa ka bhosda", "11/09/2001", "ok", cdao);
                // System.out.println(val);

                // boolean ans = ldao.customerLogin(1, "shlok");
                // boolean a1 = cdao.accountLogin("00000000");
                // boolean a2 = cdao.accountLogin("00000006"); 

                // boolean a3 = cdao.deleteAccount(acc, adao);
                // System.out.println(val + " " + ans + " " + a1 + " " + a2 + " " + a3);

                // Transaction trans = new Transaction(100, "NULL", "NULL", "withdraw", "cash");
                // Transaction tans = tdao.addTransaction(trans);
                // tans.printAll();
                //ArrayList<Transaction> trs =  adao.getTransactions(acc, "12/12/2000", "12/12/2024", tdao);
                ArrayList<Transaction> trs =  tdao.getTransactions(acc, "2000-12-12", "2026-12-12");
                for (int i = 0; i < trs.size(); i++) {
                    trs.get(i).printAll();
                }
        
                daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
            } catch (Exception e) {
                // End transaction boundary with failure
                daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                e.printStackTrace();
            }

		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
    }
}
