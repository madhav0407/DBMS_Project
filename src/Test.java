
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
                int val = ldao.signUp("sl", "1343512", "teri maa ka bhosda", "11/09/2001", "ok", cdao);
                System.out.println(val);

                boolean ans = ldao.customerLogin(1, "shlok");
                boolean a1 = cdao.accountLogin("00000000");
                boolean a2 = cdao.accountLogin("00000006"); 
                boolean a3 = cdao.deleteAccount("00000000");
                boolean a4 = cdao.deleteAccount("00000006");
                System.out.println(val + " " + ans + " " + a1 + " " + a2 + " " + a3 + " " + a4);
                // End transaction boundary with success
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
