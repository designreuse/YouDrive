-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: YouDrive
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.13.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Comments`
--

DROP TABLE IF EXISTS `Comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createdOn` date NOT NULL,
  `comment` text NOT NULL,
  `author` int(11) NOT NULL,
  `vehicleID` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author` (`author`),
  KEY `vehicleID` (`vehicleID`),
  CONSTRAINT `Comments_ibfk_1` FOREIGN KEY (`author`) REFERENCES `Users` (`id`),
  CONSTRAINT `Comments_ibfk_2` FOREIGN KEY (`vehicleID`) REFERENCES `Vehicles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comments`
--

LOCK TABLES `Comments` WRITE;
/*!40000 ALTER TABLE `Comments` DISABLE KEYS */;
INSERT INTO `Comments` VALUES (1,'2013-11-18','This is a test comment.',1,2),(2,'2013-11-18','This is my second comment.',1,2),(3,'2013-11-18','This is a comment for the lambo.',1,1),(4,'2013-11-18','Jane entered some bogus comments.',2,2);
/*!40000 ALTER TABLE `Comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Locations`
--

DROP TABLE IF EXISTS `Locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Locations`
--

LOCK TABLES `Locations` WRITE;
/*!40000 ALTER TABLE `Locations` DISABLE KEYS */;
INSERT INTO `Locations` VALUES (1,'Downtown Location','123 East Broadstreet Rd, Athens GA 30602',2),(2,'Five Points Location','456 S Milledge Ave, Athens GA 30605',5),(3,'Westside Location','789 Olympic Drive, Athens GA 30601',100),(4,'Epps Bridge Location','379 Epps Bridge Pkwy, Athens Ga 30601',20);
/*!40000 ALTER TABLE `Locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Memberships`
--

DROP TABLE IF EXISTS `Memberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Memberships` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Memberships`
--

LOCK TABLES `Memberships` WRITE;
/*!40000 ALTER TABLE `Memberships` DISABLE KEYS */;
INSERT INTO `Memberships` VALUES (1,'6 month plan',150.00,6),(2,'12 month plan',290.00,12),(3,'24 month plan',430.00,24),(4,'Monthly plan',50.00,1);
/*!40000 ALTER TABLE `Memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservations`
--

DROP TABLE IF EXISTS `Reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) NOT NULL,
  `locationID` int(11) NOT NULL,
  `vehicleID` int(11) NOT NULL,
  `timePickup` datetime NOT NULL,
  `reservationStart` datetime NOT NULL,
  `reservationEnd` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customerID` (`customerID`),
  KEY `locationID` (`locationID`),
  KEY `vehicleID` (`vehicleID`),
  CONSTRAINT `Reservations_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `Users` (`id`),
  CONSTRAINT `Reservations_ibfk_2` FOREIGN KEY (`locationID`) REFERENCES `Locations` (`id`),
  CONSTRAINT `Reservations_ibfk_3` FOREIGN KEY (`vehicleID`) REFERENCES `Vehicles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservations`
--

LOCK TABLES `Reservations` WRITE;
/*!40000 ALTER TABLE `Reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `state` varchar(3) DEFAULT NULL,
  `license` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `address` text,
  `ccType` varchar(255) DEFAULT NULL,
  `ccNumber` varchar(17) DEFAULT NULL,
  `ccSecurityCode` varchar(10) DEFAULT NULL,
  `ccExpirationDate` varchar(7) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `memberExpiration` date DEFAULT NULL,
  `membershipLevel` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `membershipLevel` (`membershipLevel`),
  CONSTRAINT `Users_ibfk_1` FOREIGN KEY (`membershipLevel`) REFERENCES `Memberships` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'jane','test','Jane','Ullah',NULL,NULL,'janeullah@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(2,'james','test','James','Vaughan',NULL,NULL,'jamesv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(3,'trevor','test','Trevor','Wilson',NULL,NULL,'trevv@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL),(4,'rod','test','Rod','Rashidi',NULL,NULL,'rod@gmail.com',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VehicleTypes`
--

DROP TABLE IF EXISTS `VehicleTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VehicleTypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `hourlyPrice` decimal(10,2) NOT NULL,
  `dailyPrice` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VehicleTypes`
--

LOCK TABLES `VehicleTypes` WRITE;
/*!40000 ALTER TABLE `VehicleTypes` DISABLE KEYS */;
INSERT INTO `VehicleTypes` VALUES (1,'Regular ',40.00,200.00),(2,'Midsize',70.00,400.00),(3,'Luxury',150.00,1000.00);
/*!40000 ALTER TABLE `VehicleTypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vehicles`
--

DROP TABLE IF EXISTS `Vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vehicles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `make` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `year` int(11) NOT NULL,
  `tag` varchar(255) NOT NULL,
  `mileage` int(11) NOT NULL,
  `lastServiced` date NOT NULL,
  `isAvailable` tinyint(1) DEFAULT '0',
  `vehicleType` int(11) NOT NULL,
  `assignedLocation` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag` (`tag`),
  KEY `vehicleType` (`vehicleType`),
  KEY `assignedLocation` (`assignedLocation`),
  CONSTRAINT `Vehicles_ibfk_1` FOREIGN KEY (`vehicleType`) REFERENCES `VehicleTypes` (`id`),
  CONSTRAINT `Vehicles_ibfk_2` FOREIGN KEY (`assignedLocation`) REFERENCES `Locations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vehicles`
--

LOCK TABLES `Vehicles` WRITE;
/*!40000 ALTER TABLE `Vehicles` DISABLE KEYS */;
INSERT INTO `Vehicles` VALUES (1,'Lamborghini','Aventador Coupe',2013,'AVG12345',100000,'2013-11-01',0,3,4),(2,'Hyundai','Elantra',2007,'DEF789456',50000,'2013-06-03',0,1,1),(3,'Dodge','Durango',2010,'GHI589623',75000,'2013-07-01',0,2,1),(4,'Toyota','Camry',2005,'TYU48965',80000,'2013-11-05',0,1,3),(5,'Dummy','Dummy',2013,'ABCTALKAM',9000,'2013-11-20',0,1,4);
/*!40000 ALTER TABLE `Vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-18 23:33:11
