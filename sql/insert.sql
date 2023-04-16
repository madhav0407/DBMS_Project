insert into branch(branchID, branchLoc, branchContact) VALUES
(1, 'Rewa', '9829986134'),
(2,'Mumbai', '9910093697'),
(3, 'Goa', '9011496298');

insert into customer(customerID, c_name, phone_num, c_address, dob) VALUES
(1, 'Shlok Agrawal', '9829986134', 'IIITB', '2002-12-26'),
(2, 'Madhav Sood', '9910093697', 'IIITB', '2003-07-04'),
(3, 'Nilay Kamat', '9011496298', 'IIITB', '1987-2-11');

insert into login(pass, customerID) VALUES
('shlok', 1),
('madhav', 2),
('nilay', 3);

insert into account(customerID, accountNumber, balance, minBalance, branchID, accountStatus) VALUES
(1, '11111111', 5234789.00, 10000.00, 1, 1),
(2, '22222222', 8941893.00, 10000.00, 2, 1),
(3, '33333333', 889.00, 100.00, 3, 1),
(1, '44444444', 12348672.00, 0, 1, 1);

insert into debitCard(accountNumber, cardNum, expiryDate, cvv, nameOnCard) VALUES
('11111111', '1111111111111111', '2026-12-2', 111, 'Shlok Agrawal'),
('22222222', '2222222222222222', '2026-11-7', 222, 'Madhav Sood');

insert into transaction(transactionID, amountTransferred, debitedFromAcc, creditedToAcc, transactionDate, transactionType, paymentMtd) VALUES
(1, 1000.00, '11111111', '22222222', '2023-04-15', 'transfer', 'card'),
(2, 500.00, NULL, '22222222', '2023-04-15', 'deposit', 'cash');