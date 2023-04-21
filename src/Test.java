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
                Customer customer = new Customer("shlok", "12", "12", "22/12/2002");
                customer.setCustomerID(1);

                // System.out.println(acc.getAccountNum() + " " + acc.getAccountStatus() + " " + acc.getBalance() + " " + acc.getBranchID() + " " + acc.getCustomerID() + " " + acc.getMinBalance());
                // int val = ldao.signUp("sl", "1343512", "teri maa ka bhosda", "11/09/2001", "ok", cdao);
                // System.out.println(val);

                // boolean ans = ldao.customerLogin(1, "shlok");
                // boolean a1 = cdao.accountLogin("00000000");
                // boolean a2 = cdao.accountLogin("00000006"); 

                // boolean a3 = cdao.deleteAccount(acc, adao);
                // System.out.println(val + " " + ans + " " + a1 + " " + a2 + " " + a3);

                //Transaction trans = new Transaction(100, "NULL", "NULL", "withdraw", "cash");
                //Transaction tans = tdao.addTransaction(trans);
                //tans.printAll();
                //ArrayList<Transaction> trs =  adao.getTransactions(acc, "12/12/2000", "21/12/2222", tdao);
                //ArrayList<Transaction> trs =  tdao.getTransactions(acc, "2000-12-12", "2026-12-12");

                //ArrayList<Transaction> trs =  cdao.getTransactions(customer);
                // for (int i = 0; i < trs.size(); i++) {
                //     trs.get(i).printAll();
                //     System.out.println();
                // }
                //cdao.createAccount(2, 1234, 10, 2, adao);
                Account acc2 = adao.getAccount("00000004");
                //cdao.deleteAccount(acc2, adao);
                float bal = adao.getBalance(acc);
                float bal2 = adao.getBalance(acc2);
                System.out.println(bal);
                System.out.println(bal2);
                System.out.println();
                Transaction trans = adao.transfer(acc, acc2, 2000, tdao);
                bal = adao.getBalance(acc);
                bal2 = adao.getBalance(acc2);
                System.out.println(bal);
                System.out.println(bal2);
                System.out.println();
                trans.printAll();
                
        
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
