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

DROP TABLE IF EXISTS group_dtl;
CREATE TABLE group_dtl( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL, companyid INT NOT NULL, parentid INT);

ALTER TABLE group_dtl ADD FOREIGN KEY ( companyid ) REFERENCES company( id ) ;
ALTER TABLE group_dtl ADD FOREIGN KEY ( parentid ) REFERENCES group_dtl( id ) ;

DROP TABLE IF EXISTS item_dtl;
CREATE TABLE item_dtl( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL, groupid INT NOT NULL,shade VARCHAR2(50) NOT NULL,description VARCHAR2(50) NOT NULL,uom VARCHAR2(50) NOT NULL,totalquandity INT,totalpackets INT);
ALTER TABLE item_dtl ADD FOREIGN KEY (groupid) REFERENCES group_dtl(id) ;
DROP TABLE IF EXISTS item_dtl_trans;
CREATE TABLE item_dtl_trans( `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR2(50) NOT NULL,itemid INT);
ALTER TABLE item_dtl_trans ADD FOREIGN KEY (itemid) REFERENCES item_dtl_trans(id) ;
