create database NanaBank;
Use NanaBank;
CREATE TABLE Clients (
    payeeAddress VARCHAR(150) NOT NULL PRIMARY KEY,
    firstname VARCHAR(150) NOT NULL,
    lastname VARCHAR(150) NOT NULL,
    gender VARCHAR(150) NOT NULL,
    DateOfBirth VARCHAR(150) NOT NULL,
    password VARCHAR(150) NOT NULL,
    DateCreated date
);

CREATE TABLE Account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(150) NOT NULL,
    balance DECIMAL(19, 4) NOT NULL,
    owner VARCHAR(150),  -- This will reference payeeAddress in Clients
    account_type VARCHAR(50),  -- Discriminator column for inheritance
    withdrawLimit DECIMAL(19, 4),  -- Only for SavingsAccount
    DateCreated date,
    FOREIGN KEY (owner) REFERENCES Clients(payeeAddress) ON DELETE CASCADE
);

CREATE TABLE Transactions(
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(150) NOT NULL,
    reciever VARCHAR(150), 
	Amount DECIMAL(19, 4) NOT NULL,
    Message VARCHAR(255), 
    dateCreated varchar(255),
    timecreated varchar(255),
    FOREIGN KEY (sender) REFERENCES Clients(payeeAddress) ON DELETE CASCADE
);


CREATE VIEW TransactionView AS
SELECT 
    t.id ,
    c.PayeeAddress as sender,
    t.reciever,
    t.amount,
    t.message,
    t.datecreated,
    t.timecreated
FROM 
    Clients c
JOIN 
    transactions t ON c.PayeeAddress = t.sender;
    

CREATE VIEW ClientView AS
SELECT 
    a.id ,
    c.PayeeAddress,
    c.FirstName,
    c.LastName,
    a.account_type,
    a.account_number,
    a.balance,
	c.DateCreated
FROM 
    Clients c
JOIN 
    Account a ON c.PayeeAddress = a.PayeeAddress;
    
    
CREATE VIEW AccountView AS
SELECT 
    a.id,
    c.PayeeAddress,
    a.account_type,
    a.account_number,
    a.balance,
    a.withdrawLimit,
    a.transaction_limit,
	a.DateCreated
FROM 
    Clients c
JOIN 
    Account a ON c.PayeeAddress = a.PayeeAddress;






create table checking_account(
ID int primary key auto_increment,
Account_id varchar(255),
Owner varchar(255),
AccountNumber varchar(255),
TransactionLimit int,
Balance double
);
insert into checking_account(Account_id,owner,accountNumber,TransactionLimit,balance) values("CK00A","@kJonathan1","1111 2222",5,500000.0);
alter table checking_account add foreign key(Account_id) references Account(Account_id) on delete cascade;
alter table checking_account  add foreign key(owner) references clients(PayeeAddress) on delete cascade;

create table savings_account(
ID int primary key auto_increment,
Account_id varchar(255),
Owner varchar(255),
AccountNumber varchar(255),
WithdrawLimit int,
Balance double
);
insert into savings_account(Account_id,owner,accountNumber,WithdrawLimit,balance) values("SA00A","@kJonathan1","2222 1111",2000,500000.0);
alter table savings_account add foreign key(Account_id) references Account(Account_id) on delete cascade;
alter table savings_account  add foreign key(owner) references clients(PayeeAddress) on delete cascade;

create table Account(
Account_id varchar(255) primary key,
AccountName varchar(255) unique
);
insert into Account(Account_id,AccountName)values("CK00A","CHECKING ACCOUNT"),("SA00A","SAVINGS ACCOUNT");


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
INSERT INTO users (username, password) VALUES ('user', '123');





CREATE VIEW viewAllClient AS
SELECT c.ID AS client_id,
       c.FirstName AS Firstname,
       c.LastName AS Lastname,
       c.PayeeAddress AS PayeeAddress,
       s.AccountNumber AS SavingsAccount,
       k.AccountNumber AS CheckingAccount
FROM Clients c
JOIN SavingsAccount s ON c.ID = s.ID
JOIN CheckingAccount k ON c.ID = k.ID;

