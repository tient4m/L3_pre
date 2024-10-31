-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: l3_pre
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `certificate`
--

DROP TABLE IF EXISTS `certificate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certificate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `field` varchar(255) DEFAULT NULL,
  `issue_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `certificate_ibfk_1` (`employee_id`),
  CONSTRAINT `certificate_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (25,NULL,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(26,NULL,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(27,NULL,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(28,NULL,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(29,NULL,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(30,NULL,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(31,NULL,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(32,NULL,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(33,NULL,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(34,NULL,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(35,26,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(36,26,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(37,28,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(38,28,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL),(39,55,'Certified Java Developer',NULL,'2018-07-20 07:00:00.000000',NULL),(40,55,'Certified Scrum Master',NULL,'2020-11-15 07:00:00.000000',NULL);
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-31 15:02:17
