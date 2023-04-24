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