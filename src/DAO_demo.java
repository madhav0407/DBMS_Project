import java.util.*;

public class DAO_demo {
    public static DAO_Factory daoFactory;

    public void CustomerMenu(Customer cust, Scanner sc, DAO_Factory daoFactory) {
        System.out.println("Hello " + cust.getName() + "!");
        try {
            CustomerDAO cdao = daoFactory.getCustomerDao();
            AccountDAO adao = daoFactory.getAccountDao();
            BranchDAO bdao = daoFactory.getBranchDAO();
            DebitCardDAO ddao = daoFactory.getDebitCardDAO();
            // TransactionDAO tdao = daoFactory.getTransactionDAO();
            // LoginDAO ldao = daoFactory.getLoginDao();
            // AdminDAO idao = daoFactory.getAdminDAO();
            while (true) {
                System.out.println(
                        "What would you like to do?\n1. Account Login\n2. Create Account\n3. Delete Account\n4. Exit");
                int choice = sc.nextInt();
                if (choice == 1) {
                    String accountNum;
                    System.out.println("Enter your account number: ");
                    accountNum = sc.next();
                    Account acc = cdao.accountLogin(cust, accountNum, adao);
                    DAO_demo obj = new DAO_demo();
                    obj.AccountMenu(acc, sc, daoFactory);
                } else if (choice == 2) {
                    int customerID, branchID;
                    float balance, minBalance;
                    System.out.println("Enter your customerID: ");
                    customerID = sc.nextInt();
                    ArrayList<Branch> branches = bdao.getBranches();
                    for (int i = 0; i < branches.size(); i++) {
                        branches.get(i).printAll();
                    }
                    System.out.println("Enter ID of branch that you would like to join: ");
                    branchID = sc.nextInt();
                    System.out.println("Enter your balance: ");
                    balance = sc.nextFloat();
                    System.out.println("Enter the minimum balance your account must store: ");
                    minBalance = sc.nextFloat();
                    Account acc = cdao.createAccount(customerID, balance, minBalance, branchID, adao);
                    DAO_demo obj = new DAO_demo();
                    obj.AccountMenu(acc, sc, daoFactory);
                } else if (choice == 3) {
                    String accnum;
                    System.out.println("Enter account number of account to be deleted: ");
                    accnum = sc.next();
                    Account acc = adao.getAccount(accnum);
                    Boolean flg = cdao.deleteAccount(acc, adao);
                    if (flg == true) {
                        System.out.println("The account has been deleted!");
                    } else {
                        System.out.println("Unable to delete account. Kindly retry.");
                    }
                } else if (choice == 4) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AccountMenu(Account account, Scanner sc, DAO_Factory daofactory) {
        System.out.println("Hello!");
        try {
            AccountDAO adao = daoFactory.getAccountDao();
            DebitCardDAO ddao = daoFactory.getDebitCardDAO();
            TransactionDAO tdao = daoFactory.getTransactionDAO();
            // LoginDAO ldao = daoFactory.getLoginDao();
            // CustomerDAO cdao = daoFactory.getCustomerDao();
            // AdminDAO idao = daoFactory.getAdminDAO();
            // BranchDAO bdao = daoFactory.getBranchDAO();

            while (true) {
                System.out.println(
                        "What would you like to do?\n1. Withdraw\n2. Deposit\n3. Transfer\n4. Get Balance\n5. Get Transactions\n6. Add Debit Card\n7. Get total spending\n8. Exit");
                int choice = sc.nextInt();
                if (choice == 1) {
                    float amt;
                    System.out.println("Enter amount to be withdrawn: ");
                    amt = sc.nextFloat();
                    Transaction transfer = new Transaction();
                    while (true) {
                        System.out.println("What would you like to withdraw amount using?\n1. Account\2. Debit Card");
                        System.out.println("Enter choice: ");
                        int ch = sc.nextInt();
                        if (ch == 1) {
                            transfer = adao.withdraw(account, amt, tdao);
                            break;
                        } else if (ch == 2) {
                            String cardnum;
                            System.out.println("Enter card number: ");
                            cardnum = sc.next();
                            DebitCard db = ddao.getCard(cardnum);
                            transfer = ddao.withdraw(db, amt, tdao);
                            break;
                        } else {
                            System.out.println("Enter valid input!");
                        }
                    }
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
                            adao = daoFactory.getAccountDao();
                            tdao = daoFactory.getTransactionDAO();
                            AccountDAO transf_acc = daoFactory.getAccountDao();
                            Account acc = transf_acc.getAccount(transfer_account);
                            Transaction transfer = new Transaction();

                            while (true) {
                                System.out.println(
                                        "What would you like to transfer amount using?\n1. Account\2. Debit Card");
                                System.out.println("Enter choice: ");
                                int ch = sc.nextInt();
                                if (ch == 1) {
                                    transfer = adao.transfer(account, acc, amt, tdao);
                                    break;
                                } else if (ch == 2) {
                                    String cardnum;
                                    System.out.println("Enter card number: ");
                                    cardnum = sc.next();
                                    DebitCard db = ddao.getCard(cardnum);
                                    transfer = ddao.transfer(db, acc, amt, tdao);
                                    break;
                                } else {
                                    System.out.println("Enter valid input!");
                                }
                            }
                            transfer.printAll();
                            System.out.println();
                            // daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                        } catch (Exception e) {
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                            e.printStackTrace();
                        }
                    }
                } else if (choice == 4) {
                    float bal = adao.getBalance(account);
                    System.out.println("Balance: " + bal);
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
                    String name;
                    System.out.println("Enter name on card: ");
                    name = sc.next();
                    DebitCard db = adao.addCard(ddao, account, name);
                    System.out.println("Card Number: " + db.getCardNum());
                    System.out.println("CVV: " + db.getCVV());
                    System.out.println("Expiration Date: " + db.getExpDate());
                } else if (choice == 7) {
                    float spending = adao.getSpending(account);
                    System.out.println("Your total spending is: " + spending);
                } else if (choice == 8) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AdminMenu(Admin adm, Scanner sc, DAO_Factory daoFactory) {
        System.out.println("Hello " + adm.getName() + "!");
        try {
            // DebitCardDAO ddao = daoFactory.getDebitCardDAO();
            // LoginDAO ldao = daoFactory.getLoginDao();
            // CustomerDAO cdao = daoFactory.getCustomerDao();
            // BranchDAO bdao = daoFactory.getBranchDAO();
            // TransactionDAO tdao = daoFactory.getTransactionDAO();
            while (true) {
                System.out.println("What would you like to do?\n1. Get Accounts\n2. Exit");
                int choice = sc.nextInt();
                if (choice == 1) {
                    try {
                        daoFactory.activateConnection();
                        AdminDAO idao = daoFactory.getAdminDAO();
                        AccountDAO adao = daoFactory.getAccountDao();
                        idao = daoFactory.getAdminDAO();
                        adao = daoFactory.getAccountDao();
                        ArrayList<Account> accounts = idao.getAccounts(adm, adao);
                        for (int i = 0; i < accounts.size(); i++) {
                            accounts.get(i).printAll();
                        }
                        System.out.println("");
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                    } catch (Exception e) {
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                    }

                } else if (choice == 2) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                            continue;
                        }
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                        obj.AdminMenu(adm, sc, daoFactory);
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
                        BranchDAO bdao = daoFactory.getBranchDAO();
                        TransactionDAO tdao = daoFactory.getTransactionDAO();
                        Customer cust = ldao.customerLogin(custID, pass, cdao);
                        if (cust.getCustomerID() == -1) {
                            System.out.println("Enter valid credentials!");
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                            continue;
                        }
                        obj.CustomerMenu(cust, sc, daoFactory);
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                    } catch (Exception e) {
                        daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                        e.printStackTrace();
                    }
                } else if (choice == 3) {
                    System.out.println("What would you like to sign up as?\n1. Admin\n2. Customer\n3. Go Back");
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
                            AccountDAO adao = daoFactory.getAccountDao();
                            AdminDAO idao = daoFactory.getAdminDAO();
                            Admin adm = ldao.adminSignUp(name, pass, idao);
                            // System.out.println("Your ID is: " + adm.getAdminID());
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);

                            if (adm.getAdminID() != -1) {
                                System.out.println("Your ID is: " + adm.getAdminID());
                                obj.AdminMenu(adm, sc, daoFactory);
                            } else {
                                System.out.println("Sign up failed.");
                                System.out.println("Please enter valid details.");
                                continue;
                            }
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
                            TransactionDAO tdao = daoFactory.getTransactionDAO();
                            BranchDAO bdao = daoFactory.getBranchDAO();
                            AccountDAO adao = daoFactory.getAccountDao();
                            Customer cust = ldao.customerSignUp(name, phnum, address, dob, pass, cdao);
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.COMMIT);
                            if (cust.getCustomerID() != -1) {
                                System.out.println("Your ID is: " + cust.getCustomerID());
                                obj.CustomerMenu(cust, sc, daoFactory);
                            } else {
                                System.out.println("Sign up failed.");
                                System.out.println("Please enter valid details.");
                                continue;
                            }
                        } catch (Exception e) {
                            daoFactory.deactivateConnection(DAO_Factory.TXN_STATUS.ROLLBACK);
                            e.printStackTrace();
                        }
                    } else if (choice == 3) {
                        continue;
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
