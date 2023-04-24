alter table customerlogin 
    add constraint fk_customerID FOREIGN KEY (customerID) REFERENCES customer(customerID);

alter table adminlogin 
    add constraint fk_adminID FOREIGN KEY (adminID) REFERENCES admin(adminID);

alter table branch
    add constraint fk_branch_adminID FOREIGN KEY (managerID) REFERENCES admin(adminID); 

alter table account
    add constraint fk_accCustomerID FOREIGN KEY (customerID) REFERENCES customer(customerID);

alter table account
    add constraint fk_branchID FOREIGN KEY (branchID) REFERENCES branch(branchID);

alter table debitCard
    add constraint fk_accountNum FOREIGN KEY (accountNumber) REFERENCES account(accountNumber);

alter table transaction
    add constraint fk_debitedAcc FOREIGN KEY (debitedFromAcc) REFERENCES account(accountNumber);

alter table transaction
    add constraint fk_creditedAcc FOREIGN KEY (creditedToAcc) REFERENCES account(accountNumber);
