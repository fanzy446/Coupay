-- MySQL dump 10.13  Distrib 5.6.13, for Win64 (x86_64)
--
-- Host: localhost    Database: coupon
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `accountId` int(11) NOT NULL,
  `balance` double(10,2) DEFAULT NULL,
  `payPassword` char(128) NOT NULL,
  PRIMARY KEY (`accountId`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bindedbankaccount`
--

DROP TABLE IF EXISTS `bindedbankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bindedbankaccount` (
  `bankAccountId` varchar(25) NOT NULL,
  `registerId` int(11) DEFAULT NULL,
  `bank` varchar(30) NOT NULL,
  PRIMARY KEY (`bankAccountId`),
  KEY `registerId` (`registerId`),
  CONSTRAINT `bindedbankaccount_ibfk_1` FOREIGN KEY (`registerId`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bindedbankaccount`
--

LOCK TABLES `bindedbankaccount` WRITE;
/*!40000 ALTER TABLE `bindedbankaccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `bindedbankaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `couponId` int(11) NOT NULL AUTO_INCREMENT,
  `merchantId` int(11) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date NOT NULL,
  `couponType` varchar(10) NOT NULL,
  `discountRate` double(10,2) DEFAULT NULL,
  `decription` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`couponId`),
  KEY `merchantId` (`merchantId`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`merchantId`) REFERENCES `merchant` (`merchantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `couponcollection`
--

DROP TABLE IF EXISTS `couponcollection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `couponcollection` (
  `customerId` int(11) NOT NULL DEFAULT '0',
  `couponId` int(11) NOT NULL DEFAULT '0',
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`customerId`,`couponId`),
  KEY `couponId` (`couponId`),
  CONSTRAINT `couponcollection_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `couponcollection_ibfk_2` FOREIGN KEY (`couponId`) REFERENCES `coupon` (`couponId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `couponcollection`
--

LOCK TABLES `couponcollection` WRITE;
/*!40000 ALTER TABLE `couponcollection` DISABLE KEYS */;
/*!40000 ALTER TABLE `couponcollection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerId` int(11) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  KEY `fk_customer_register` (`customerId`),
  CONSTRAINT `fk_customer_register` FOREIGN KEY (`customerId`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `customercouponcollection`
--

DROP TABLE IF EXISTS `customercouponcollection`;
/*!50001 DROP VIEW IF EXISTS `customercouponcollection`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `customercouponcollection` (
  `customerId` tinyint NOT NULL,
  `merchantId` tinyint NOT NULL,
  `couponId` tinyint NOT NULL,
  `number` tinyint NOT NULL,
  `startDate` tinyint NOT NULL,
  `endDate` tinyint NOT NULL,
  `couponType` tinyint NOT NULL,
  `discountRate` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `customerinportantinformation`
--

DROP TABLE IF EXISTS `customerinportantinformation`;
/*!50001 DROP VIEW IF EXISTS `customerinportantinformation`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `customerinportantinformation` (
  `customerId` tinyint NOT NULL,
  `loginPasswor` tinyint NOT NULL,
  `payPassword` tinyint NOT NULL,
  `balance` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `depositrecord`
--

DROP TABLE IF EXISTS `depositrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `depositrecord` (
  `depositId` int(11) NOT NULL AUTO_INCREMENT,
  `bankAccountId` varchar(25) DEFAULT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `depositDate` date NOT NULL,
  PRIMARY KEY (`depositId`),
  KEY `bankAccountId` (`bankAccountId`),
  CONSTRAINT `depositrecord_ibfk_1` FOREIGN KEY (`bankAccountId`) REFERENCES `bindedbankaccount` (`bankAccountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `depositrecord`
--

LOCK TABLES `depositrecord` WRITE;
/*!40000 ALTER TABLE `depositrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `depositrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followdmerchant`
--

DROP TABLE IF EXISTS `followdmerchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followdmerchant` (
  `merchantId` int(11) NOT NULL DEFAULT '0',
  `customerId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`merchantId`,`customerId`),
  KEY `customerId` (`customerId`),
  CONSTRAINT `followdmerchant_ibfk_1` FOREIGN KEY (`merchantId`) REFERENCES `merchant` (`merchantId`),
  CONSTRAINT `followdmerchant_ibfk_2` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followdmerchant`
--

LOCK TABLES `followdmerchant` WRITE;
/*!40000 ALTER TABLE `followdmerchant` DISABLE KEYS */;
/*!40000 ALTER TABLE `followdmerchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friendship`
--

DROP TABLE IF EXISTS `friendship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friendship` (
  `customerId1` int(11) NOT NULL DEFAULT '0',
  `customerId2` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`customerId1`,`customerId2`),
  KEY `customerId2` (`customerId2`),
  CONSTRAINT `friendship_ibfk_1` FOREIGN KEY (`customerId1`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `friendship_ibfk_2` FOREIGN KEY (`customerId2`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friendship`
--

LOCK TABLES `friendship` WRITE;
/*!40000 ALTER TABLE `friendship` DISABLE KEYS */;
/*!40000 ALTER TABLE `friendship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant` (
  `merchantId` int(11) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  KEY `fk_merchant_register` (`merchantId`),
  CONSTRAINT `fk_merchant_register` FOREIGN KEY (`merchantId`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant`
--

LOCK TABLES `merchant` WRITE;
/*!40000 ALTER TABLE `merchant` DISABLE KEYS */;
/*!40000 ALTER TABLE `merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `receiverId` int(11) DEFAULT NULL,
  `content` varchar(200) NOT NULL,
  `messageType` varchar(15) NOT NULL,
  PRIMARY KEY (`messageId`),
  KEY `receiverId` (`receiverId`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`receiverId`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productinfomation`
--

DROP TABLE IF EXISTS `productinfomation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productinfomation` (
  `productId` varchar(20) NOT NULL DEFAULT '',
  `transactionId` char(22) NOT NULL DEFAULT '',
  PRIMARY KEY (`productId`,`transactionId`),
  KEY `transactionId` (`transactionId`),
  CONSTRAINT `productinfomation_ibfk_1` FOREIGN KEY (`transactionId`) REFERENCES `transactionrecord` (`transactinId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productinfomation`
--

LOCK TABLES `productinfomation` WRITE;
/*!40000 ALTER TABLE `productinfomation` DISABLE KEYS */;
/*!40000 ALTER TABLE `productinfomation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `register` (
  `registerId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `loginPasswor` char(128) NOT NULL,
  `email` char(40) DEFAULT NULL,
  `phoneNumber` varchar(40) DEFAULT NULL,
  `grade` int(11) NOT NULL,
  PRIMARY KEY (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register`
--

LOCK TABLES `register` WRITE;
/*!40000 ALTER TABLE `register` DISABLE KEYS */;
/*!40000 ALTER TABLE `register` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharing`
--

DROP TABLE IF EXISTS `sharing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sharing` (
  `sharingId` int(11) NOT NULL AUTO_INCREMENT,
  `shareId` int(11) DEFAULT NULL,
  `content` varchar(140) NOT NULL,
  `sharingDate` datetime NOT NULL,
  PRIMARY KEY (`sharingId`),
  KEY `shareId` (`shareId`),
  KEY `sharingDateIndex` (`sharingDate`),
  CONSTRAINT `sharing_ibfk_1` FOREIGN KEY (`shareId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharing`
--

LOCK TABLES `sharing` WRITE;
/*!40000 ALTER TABLE `sharing` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactionrecord`
--

DROP TABLE IF EXISTS `transactionrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactionrecord` (
  `transactinId` char(22) NOT NULL,
  `sender` int(11) DEFAULT NULL,
  `receiver` int(11) DEFAULT NULL,
  `amount` double(10,2) NOT NULL,
  `transactionDate` datetime NOT NULL,
  `transactionType` varchar(15) NOT NULL,
  `detail` text,
  PRIMARY KEY (`transactinId`),
  KEY `sender` (`sender`),
  KEY `receiver` (`receiver`),
  KEY `transactionDateIndex` (`transactionDate`),
  CONSTRAINT `transactionrecord_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `register` (`registerId`),
  CONSTRAINT `transactionrecord_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `register` (`registerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactionrecord`
--

LOCK TABLES `transactionrecord` WRITE;
/*!40000 ALTER TABLE `transactionrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactionrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vipcard`
--

DROP TABLE IF EXISTS `vipcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vipcard` (
  `cardId` varchar(25) NOT NULL,
  `merchantId` int(11) DEFAULT NULL,
  `customerId` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  PRIMARY KEY (`cardId`),
  KEY `merchantId` (`merchantId`),
  KEY `customerId` (`customerId`),
  CONSTRAINT `vipcard_ibfk_1` FOREIGN KEY (`merchantId`) REFERENCES `merchant` (`merchantId`),
  CONSTRAINT `vipcard_ibfk_2` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vipcard`
--

LOCK TABLES `vipcard` WRITE;
/*!40000 ALTER TABLE `vipcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `vipcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `customercouponcollection`
--

/*!50001 DROP TABLE IF EXISTS `customercouponcollection`*/;
/*!50001 DROP VIEW IF EXISTS `customercouponcollection`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customercouponcollection` AS select `couponcollection`.`customerId` AS `customerId`,`coupon`.`merchantId` AS `merchantId`,`couponcollection`.`couponId` AS `couponId`,`couponcollection`.`number` AS `number`,`coupon`.`startDate` AS `startDate`,`coupon`.`endDate` AS `endDate`,`coupon`.`couponType` AS `couponType`,`coupon`.`discountRate` AS `discountRate` from (`couponcollection` join `coupon`) where (`couponcollection`.`couponId` = `coupon`.`couponId`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `customerinportantinformation`
--

/*!50001 DROP TABLE IF EXISTS `customerinportantinformation`*/;
/*!50001 DROP VIEW IF EXISTS `customerinportantinformation`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customerinportantinformation` AS select `register`.`registerId` AS `customerId`,`register`.`loginPasswor` AS `loginPasswor`,`account`.`payPassword` AS `payPassword`,`account`.`balance` AS `balance` from (`register` join `account`) where (`account`.`accountId` = `register`.`registerId`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-09-15 16:52:43
