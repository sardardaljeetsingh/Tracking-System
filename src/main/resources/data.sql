
insert into users (id, address, email, usr_psw, phone, usertype, usr_name) values (1, 'HYD', 'admin@admin.com', 'admin', 9999999, 'admin', 'admin');
insert into previliges (id, accountinfo, inventoryinfo, reports, transactions, userid) values (1, 'true', 'true','true', 'true', 1);
insert into users (id, address, email, usr_psw, phone, usertype, usr_name) values (2, 'HYD', 'user@user.com', 'pavan', 9999999, 'user', 'pavan');
insert into previliges (id, accountinfo, inventoryinfo, reports, transactions, userid) values (2, 'true', 'true','true', 'true', 2);
INSERT INTO company (id ,name,mailingname,address,country,companystate,pincode,phone,email,currencesymbol,companytype,yearstart,booksstart) VALUES (1, 'IBM','IBM','Mind Space',1,1,12345,912345,'pp@pp.com','Rs.',1,'01/04/2017','01/04/2017');
INSERT INTO stock_group_dtl(id ,name,companyid) VALUES (1, 'DUMMY',1);
INSERT INTO stock_group_dtl(id ,name,companyid,parentid) VALUES (2, 'Raymonds',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid) VALUES (1, 'DUMMY',1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (2,'Branch / Divisions',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (3,'Capital Account',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (4,'Current Assets',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (5,'Current Liabilities',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (6,'Direct Expenses',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (7,'Direct Incomes',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (8,'Fixed Assets',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (9,'Indirect Expenses',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (10,'Indirect Incomes',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (11,'Investments',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (12,'Loans (Liability)',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (13,'Misc. Expenses (ASSET)',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (14,'Purchase Accounts',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (15,'Sales Accounts',1,1);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (16,'Suspense A/c',1,1);


INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (17,'Bank Accounts',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (18,'Bank OD A/c',1,12);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (19,'Cash-in-hand',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (20,'Deposits (Asset)',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (21,'Duties & Taxes',1,5);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (22,'Loans & Advances (Asset)',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (23,'Provisions',1,5);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (24,'Reserves & Surplus',1,3);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (25,'Secured Loans',1,12);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (26,'Stock-in-hand',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (27,'Sundry Creditors',1,5);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (28,'Sundry Debtors',1,4);
INSERT INTO acc_group_dtl(id ,name,companyid,parentid) VALUES (29,'Unsecured Loans',1,12);

INSERT INTO item_dtl(id,name,groupid,shade,description,uom,totalquandity) values (1,'shirts',2,'Lenin','Lenin Shirts','kgs',1234);



