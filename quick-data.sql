INSERT INTO `Locations` VALUES (1,'Downtown Location','123 East Broadstreet Rd, Athens GA 30602',2),(2,'Five Points Location','456 S Milledge Ave, Athens GA 30605',5),(3,'Westside Location','789 Olympic Drive, Athens GA 30601',100),(4,'Epps Bridge Location','379 Epps Bridge Pkwy, Athens Ga 30601',20);

INSERT INTO `Memberships` VALUES (1,'6 month plan',150.00,6),(2,'12 month plan',290.00,12),(3,'24 month plan',430.00,24),(4,'Monthly plan',50.00,1);

INSERT INTO `Users` VALUES (1,'jane','test','Jane','Ullah',NULL,NULL,'janeullah@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW()),(2,'james','test','James','Vaughan',NULL,NULL,'jamesv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW()),(3,'trevor','test','Trevor','Wilson',NULL,NULL,'trevv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW()),(4,'rod','test','Rod','Rashidi',NULL,NULL,'rod@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NOW());

INSERT INTO `VehicleTypes` VALUES (1,'Regular ',40.00,200.00),(2,'Midsize',70.00,400.00),(3,'Luxury',150.00,1000.00);

INSERT INTO `Vehicles` VALUES (1,'Lamborghini','Aventador Coupe',2013,'AVG12345',100000,'2013-11-01',0,3,4),(2,'Hyundai','Elantra',2007,'DEF789456',50000,'2013-06-03',0,1,1),(3,'Dodge','Durango',2010,'GHI589623',75000,'2013-07-01',0,2,1),(4,'Toyota','Camry',2005,'TYU48965',80000,'2013-11-05',0,1,3),(5,'Dummy','Dummy',2013,'ABCTALKAM',9000,'2013-11-20',0,1,4);


insert into Reservations values (DEFAULT,1,1,1,NOW(),DATE_ADD(NOW(), INTERVAL 72 HOUR));


insert into ReservationStatus values (DEFAULT,1,NOW(),'Created');
