create table customerlogin (
    pass varchar(100),
    customerID int,
    constraint pk_customerlogin PRIMARY KEY (customerID)
);

create table adminlogin (
    pass varchar(100),
    adminID int,
    constraint pk_adminlogin PRIMARY KEY (adminID)
);

create table admin (
    adminID int AUTO_INCREMENT,
    name varchar(100),
    constraint pk_admin PRIMARY KEY (adminID)
);

create table branch (
    branchID int,
    managerID int,
    branchLocation varchar(100),
    branchContact varchar(100),
    constraint pk_branch PRIMARY KEY (branchID)
);

create table customer (
    customerID int AUTO_INCREMENT,
    c_name varchar(100),
    phone_num varchar(10),
    c_address varchar(1000),
    dob date,
    constraint pk_customer PRIMARY KEY (customerID)
);

create table account (
    customerID int,
    accountNumber varchar(8),
    balance decimal(19, 2), 
    minBalance decimal(19, 2),
    branchID int,
    accountStatus boolean,
    constraint pk_account PRIMARY KEY (accountNumber)
);

create table debitCard (
    accountNumber varchar(8),
    cardNum varchar(16),
    expiryDate date,
    cvv int,
    nameOnCard varchar(100),
    constraint pk_debitCard PRIMARY KEY (cardNum)
);

create table transaction (
    transactionID int AUTO_INCREMENT, 
    amountTransferred decimal(19, 2),
    debitedFromAcc varchar(8),
    creditedToAcc varchar(8), 
    transactionDate date,
    transactionType varchar(10),
    paymentMtd varchar(10),
    constraint pk_transaction PRIMARY KEY (transactionID)
);

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

insert into admin(adminID, name) VALUES 
(1, 'Vineet'),
(2, 'Vihan'),
(3, 'Varshith');

insert into adminlogin(pass, adminID) VALUES
("vineet", 1),
("vihan", 2),
("varshith", 3);

insert into branch(branchID, managerID, branchLocation, branchContact) VALUES
(1, 2, 'Rewa', '9829986134'),
(2, 1, 'Mumbai', '9910093697'),
(3, 3, 'Goa', '9011496298');

insert into customer(c_name, phone_num, c_address, dob) VALUES
('Shlok Agrawal', '9829986134', 'IIITB', '2002-12-26'),
('Madhav Sood', '9910093697', 'IIITB', '2003-07-04'),
('Nilay Kamat', '9011496298', 'IIITB', '1987-2-11');

insert into customerlogin(pass, customerID) VALUES
('shlok', 1),
('madhav', 2),
('nilay', 3);

insert into customer(c_name, phone_num, c_address, dob) VALUES
('Shlok Agrawal', '9829986134', 'IIITB', '2002-12-26'),
('Madhav Sood', '9910093697', 'IIITB', '2003-07-04'),
('Nilay Kamat', '9011496298', 'IIITB', '1987-2-11');

insert into login(pass, customerID) VALUES
('shlok', 1),
('madhav', 2),
('nilay', 3);

insert into account(customerID, accountNumber, balance, minBalance, branchID, accountStatus) VALUES
(1, '00000000', 5234789.00, 10000.00, 1, 1),
(2, '00000001', 8941893.00, 10000.00, 2, 1),
(3, '00000002', 889.00, 100.00, 3, 1),
(1, '00000003', 12348672.00, 0, 1, 1);

insert into debitCard(accountNumber, cardNum, expiryDate, cvv, nameOnCard) VALUES
('00000000', '0000000000000000', '2026-12-2', 0, 'Shlok Agrawal'),
('00000001', '0000000000000001', '2026-11-7', 1,'Madhav Sood');

insert into transaction(amountTransferred, debitedFromAcc, creditedToAcc, transactionDate, transactionType, paymentMtd) VALUES
(1000.00, '00000000', '00000001', '2023-04-15', 'transfer', 'card'),
(500.00, NULL, '00000001', '2023-04-15', 'deposit', 'cash');