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