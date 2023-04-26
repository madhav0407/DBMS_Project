import java.util.*;

public class DAO_demo {
    public static DAO_Factory daoFactory;

    public void CustomerMenu(Customer cust, CustomerDAO cdao, AccountDAO adao, Scanner sc) {
        System.out.println("Hello " + cust.getName() + "!");
        while (true) {
            System.out.println("What would you like to do?\n1. Login\n2. Create Account\n3. Delete Account\n4. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                String accountNum;
                System.out.println("Enter your account number: ");
                accountNum = sc.next();
                cdao.accountLogin(accountNum, adao);
            } else if (choice == 2) {
                return;
            } else if (choice == 3) {
                return;
            } else if (choice == 4) {
                return;
            }
        }
    }

    public void AccountMenu(Account account, TransactionDAO tdao, CustomerDAO cdao, AccountDAO adao, Scanner sc) {
        System.out.println("Hello!");
        while (true) {
            System.out.println(
                    "What would you like to do?\n1. Withdraw\n2. Deposit\n3. Transfer\n4. Get Balance\n5. Get Transactions\n6. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                float amt;
                System.out.println("Enter amount to be withdrawn: ");
                amt = sc.nextFloat();
                Transaction transfer = adao.withdraw(account, amt, tdao);
                transfer.printAll();
            } else if (choice == 2) {
                float amt;
                System.out.println("Enter amount to be deposited: ");
                amt = sc.nextFloat();
                Transaction transfer = adao.deposit(account, amt, tdao);
                transfer.printAll();
            } else if (choice == 3) {
                float amt;
                String transfer_account;
                System.out.println("Enter amount to be transferred: ");
                amt = sc.nextFloat();
                while (true) {
                    System.out.println("Enter transfer account number: ");
                    transfer_account = sc.next();
                    if (transfer_account == null) {
                        System.out.println("Enter valid transfer account number!");
                        continue;
                    }
                    try {
                        daoFactory.activateConnection();
                        AccountDAO transf_acc = daoFactory.getAccountDao();
                        Account acc = transf_acc.getAccount(transfer_account);
                        Transaction transfer = adao.transfer(account, acc, amt, tdao);
                        transfer.printAll();
                        // daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                    } catch (Exception e) {
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                        e.printStackTrace();
                    }
                }
            } else if (choice == 4) {
                float bal = adao.getBalance(account);
                System.out.println(bal);
            } else if (choice == 5) {
                String st, end;
                System.out.println("Enter start date: ");
                st = sc.next();
                System.out.println("Enter end date: ");
                end = sc.next();
                ArrayList<Transaction> transfers = adao.getTransactions(account, st, end, tdao);
                for (int i = 0; i < transfers.size(); i++) {
                    transfers.get(i).printAll();
                }
            } else if (choice == 6) {
                sc.close();
                return;
            }
        }
    }

    public void AdminMenu(Admin adm, AdminDAO idao, AccountDAO adao, Scanner sc) {
        //Scanner sc1 = new Scanner(System.in);
        System.out.println("Hello " + adm.getName() + "!");
        while (true) {
            System.out.println("What would you like to do?\n1. Get Accounts\n2. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                try{
                ArrayList<Account> accounts = idao.getAccounts(adm, adao);
                for (int i = 0; i < accounts.size(); i++) {
                    accounts.get(i).printAll();
                }
                System.out.println("");
            }
            catch(Exception e){}

            } else if (choice == 2) {
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DAO_demo obj = new DAO_demo();
        daoFactory = new DAO_Factory();
        Scanner sc = new Scanner(System.in);
        try {
            // daoFactory.activateConnection();
            System.out.println("Welcome to Bank!");

            // LoginDAO ldao = daoFactory.getLoginDao();
            // CustomerDAO cdao = daoFactory.getCustomerDao();
            // AdminDAO idao = daoFactory.getAdminDAO();
            // TransactionDAO tdao = daoFactory.getTransactionDAO();
            // AccountDAO adao = daoFactory.getAccountDao();
            // DebitCardDAO ddao = daoFactory.getDebitCardDAO();

            while (true) {
                System.out.println("Login as?\n1. Admin\n2. Customer\n3. Sign up\n4. Exit");
                System.out.println("Enter choice: ");
                int choice = 0;
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                } else {

                }

                if (choice == 1) {
                    Integer adminID;
                    String pass;
                    System.out.println("Enter your ID: ");
                    adminID = sc.nextInt();
                    System.out.println("Enter your password: ");
                    pass = sc.next();
                    try {
                        daoFactory.activateConnection();
                        LoginDAO ldao = daoFactory.getLoginDao();
                        // CustomerDAO cdao = daoFactory.getCustomerDao();
                        AdminDAO idao = daoFactory.getAdminDAO();
                        AccountDAO adao = daoFactory.getAccountDao();
                        Admin adm = ldao.adminLogin(adminID, pass, idao);
                        if (adm.getAdminID() == -1) {
                            System.out.println("Enter valid credentials!");
                            continue;
                        }
                        obj.AdminMenu(adm, idao, adao, sc);
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                    } catch (Exception e) {
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                        e.printStackTrace();
                    }
                } else if (choice == 2) {
                    Integer custID;
                    String pass;
                    System.out.println("Enter your ID: ");
                    custID = sc.nextInt();
                    System.out.println("Enter your password: ");
                    pass = sc.next();
                    try {
                        daoFactory.activateConnection();
                        LoginDAO ldao = daoFactory.getLoginDao();
                        CustomerDAO cdao = daoFactory.getCustomerDao();
                        // AdminDAO idao = daoFactory.getAdminDAO();
                        AccountDAO adao = daoFactory.getAccountDao();
                        Customer cust = ldao.customerLogin(custID, pass, cdao);
                        if (cust.getCustomerID() == -1) {
                            System.out.println("Enter valid credentials!");
                            continue;
                        }
                        // System.out.println("Your ID is: " + cust.getCustomerID());
                        obj.CustomerMenu(cust, cdao, adao, sc);
                        // daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                    } catch (Exception e) {
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                        e.printStackTrace();
                    }
                } else if (choice == 3) {
                    System.out.println("What would you like to sign up as?\n1. Admin\n2. Customer");
                    Integer user_type;
                    user_type = sc.nextInt();
                    if (user_type == 1) {
                        String name, pass;
                        System.out.println("Enter your name: ");
                        name = sc.next();
                        System.out.println("Enter your password: ");
                        pass = sc.next();
                        try {
                            daoFactory.activateConnection();
                            LoginDAO ldao = daoFactory.getLoginDao();
                            // CustomerDAO cdao = daoFactory.getCustomerDao();
                            AdminDAO idao = daoFactory.getAdminDAO();
                            Admin adm = ldao.adminSignUp(name, pass, idao);
                            System.out.println("Your ID is: " + adm.getAdminID());
                            // daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                        } catch (Exception e) {
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                            e.printStackTrace();
                        }
                    } else if (user_type == 2) {
                        String name, phnum, address, dob, pass;
                        System.out.println("Enter your name: ");
                        name = sc.next();
                        System.out.println("Enter your phone number: ");
                        phnum = sc.next();
                        System.out.println("Enter your address: ");
                        address = sc.next();
                        System.out.println("Enter your date of birth: ");
                        dob = sc.next();
                        System.out.println("Enter your password: ");
                        pass = sc.next();
                        try {
                            daoFactory.activateConnection();
                            LoginDAO ldao = daoFactory.getLoginDao();
                            CustomerDAO cdao = daoFactory.getCustomerDao();
                            // AdminDAO idao = daoFactory.getAdminDAO();
                            Customer cust = ldao.customerSignUp(name, phnum, address, dob, pass, cdao);
                            System.out.println("Your ID is: " + cust.getCustomerID());
                            // daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                        } catch (Exception e) {
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                            e.printStackTrace();
                        }
                    }

                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Enter a valid input!");
                    continue;
                }
                sc.reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
    }
}
