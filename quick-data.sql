INSERT INTO `Locations` VALUES (1,'Downtown Location','123 East Broadstreet Rd, Athens GA 30602',2),(2,'Five Points Location','456 S Milledge Ave, Athens GA 30605',5),(3,'Westside Location','789 Olympic Drive, Athens GA 30601',100),(4,'Epps Bridge Location','379 Epps Bridge Pkwy, Athens Ga 30601',20),(5,'Dummy Location','10 Downing Street, Athens GA 30601',15);

INSERT INTO `Memberships` VALUES (1,'6 month plan',150.00,6),(2,'12 month plan',290.00,12),(3,'24 month plan',430.00,24);

INSERT INTO `Users` VALUES (1,'jane','test','Jane','Ullah',NULL,NULL,'janeullah@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW(),1),(2,'james','test','James','Vaughan',NULL,NULL,'jamesv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW(),1),(3,'trevor','test','Trevor','Wilson',NULL,NULL,'trevv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW(),1),(4,'rod','test','Rod','Rashidi',NULL,NULL,'rod@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW(),1),(5,'test','test','Test','User','AL','ABC123456','test@example.com','123 Rodeo Dr, Athens GA 30605','Mastercard','1234123412341234','668','11/2014',0,'2014-05-26 12:38:12',1,'2013-11-26 12:38:12',1);

INSERT INTO `VehicleTypes` VALUES (1,'Regular ',40.00,200.00),(2,'Pickup Truck',70.00,400.00),(3,'Luxury',150.00,1000.00),(4,'Dummy Type',45.00,600.00);

INSERT INTO `Vehicles` VALUES (1,'Lamborghini','Aventador Coupe',2013,'AVG12345',100000,'2013-11-01',3,4),(2,'Hyundai','Elantra',2007,'DEF789456',50000,'2013-06-03',1,1),(3,'Dodge','Durango',2010,'GHI589623',75000,'2013-07-01',2,1),(4,'Toyota','Camry',2005,'TYU48965',80000,'2013-11-05',1,3),(5,'Dummy','Dummy',2013,'ABCTALKAM',9000,'2013-11-20',4,5),(6,'Ford','Mustang',2011,'MUS15463',50000,'2013-11-02',3,2);


INSERT INTO `Comments` VALUES (1,'2013-11-26 12:26:18','This vehicle is in great shape!',1,3),(2,'2013-11-26 12:26:56','Creating the Ford Mustang',1,6),(3,'2013-11-26 12:27:12','Comment about this dummy car.',1,5),(4,'2013-11-26 12:36:18','Changing type and location to Dummy.',1,5),(5,'2013-11-26 12:36:38','Vehicle in decent shape!',1,2);


INSERT INTO `Reservations` VALUES (1,5,5,5,'2013-11-05 10:00:00','2013-11-08 10:00:00');
INSERT INTO `ReservationStatus` VALUES (1,1,'2013-11-26 12:43:53','Created');





