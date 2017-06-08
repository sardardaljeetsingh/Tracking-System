---- LOGIN -------
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	usr_name VARCHAR2(50) NOT NULL,
	usr_psw VARCHAR2(50) NOT NULL,
	address VARCHAR2(50) NOT NULL,
	phone BIGINT NOT NULL,
	email VARCHAR2(50) NOT NULL,
	usertype VARCHAR2(50) NOT NULL,
);

DROP TABLE IF EXISTS previliges;
CREATE TABLE previliges
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	userid INT NOT NULL, 
	accountinfo VARCHAR2(50) NOT NULL,
	inventoryinfo VARCHAR2(50) NOT NULL,
	transactions VARCHAR2(50) NOT NULL,
	reports VARCHAR2(50) NOT NULL,
);
ALTER TABLE previliges ADD FOREIGN KEY ( userid ) REFERENCES users( id ) ;

---- COMPANY -------

DROP TABLE IF EXISTS company;
CREATE TABLE company
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR2(50) NOT NULL,
	mailingname VARCHAR2(50) NOT NULL,
	address VARCHAR2(100) NOT NULL,
	country INT NOT NULL,
	companystate INT NOT NULL,
	pincode INT NOT NULL,
	phone INT NOT NULL,
	email VARCHAR2(100) NOT NULL,
	currencesymbol VARCHAR2(100),
	companytype INT NOT NULL,
	yearstart VARCHAR2(100) NOT NULL,
	booksstart VARCHAR2(100) NOT NULL,
);

DROP TABLE IF EXISTS company_user;
CREATE TABLE company_user
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	companyid INT NOT NULL,
	userid INT NOT NULL,
);
ALTER TABLE company_user ADD FOREIGN KEY ( companyid ) REFERENCES company( id ) ;
ALTER TABLE company_user ADD FOREIGN KEY ( userid ) REFERENCES users( id ) ;


---- Inventory Info -------
DROP TABLE IF EXISTS stock_group_dtl;
CREATE TABLE stock_group_dtl( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL, companyid INT NOT NULL, parentid INT);

ALTER TABLE stock_group_dtl ADD FOREIGN KEY ( companyid ) REFERENCES company( id ) ;
ALTER TABLE stock_group_dtl ADD FOREIGN KEY ( parentid ) REFERENCES stock_group_dtl( id ) ;

DROP TABLE IF EXISTS item_dtl;
CREATE TABLE item_dtl( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL, groupid INT NOT NULL,shade VARCHAR2(50) NOT NULL,description VARCHAR2(50) NOT NULL,uom VARCHAR2(50) NOT NULL,totalquandity INT);
ALTER TABLE item_dtl ADD FOREIGN KEY (groupid) REFERENCES stock_group_dtl(id) ;
DROP TABLE IF EXISTS item_dtl_trans;
CREATE TABLE item_dtl_trans( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL,itemid INT,quandity INT,pices INT);
ALTER TABLE item_dtl_trans ADD FOREIGN KEY (itemid) REFERENCES item_dtl_trans(id) ;


---- Accounting INFO -------
DROP TABLE IF EXISTS acc_group_dtl;
CREATE TABLE acc_group_dtl( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL, companyid INT NOT NULL, parentid INT);

ALTER TABLE acc_group_dtl ADD FOREIGN KEY ( companyid ) REFERENCES company( id ) ;
ALTER TABLE acc_group_dtl ADD FOREIGN KEY ( parentid ) REFERENCES acc_group_dtl( id ) ;

DROP TABLE IF EXISTS ledger;
CREATE TABLE ledger( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(100) NOT NULL,alias VARCHAR2(100) NOT NULL, groupid int,mailingname VARCHAR2(100) NOT NULL,mailingaddress VARCHAR2(500) NOT NULL,mailingstate VARCHAR2(100) NOT NULL,saletaxno BIGINT,taxpan BIGINT);
ALTER TABLE ledger ADD FOREIGN KEY ( groupid ) REFERENCES acc_group_dtl( id ) ;







--------------------- TRACKING SYSTEM ---------------------------------
DROP TABLE IF EXISTS employees;
CREATE TABLE employees
(
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	emp_name VARCHAR2(50) NOT NULL,
);
