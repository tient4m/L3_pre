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
-- Table structure for table `event_form_history`
--

DROP TABLE IF EXISTS `event_form_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_form_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_form_id` int NOT NULL,
  `request_date` datetime(6) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_event_form` (`event_form_id`),
  CONSTRAINT `fk_event_form` FOREIGN KEY (`event_form_id`) REFERENCES `event_form` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_form_history`
--

LOCK TABLES `event_form_history` WRITE;
/*!40000 ALTER TABLE `event_form_history` DISABLE KEYS */;
INSERT INTO `event_form_history` VALUES (39,32,'2024-10-05 15:29:44.212000','đồng ý','APPROVED'),(40,34,'2024-10-05 15:39:04.893000',NULL,'PENDING'),(41,34,'2024-10-05 15:40:05.285000','Đồng ý','APPROVED'),(42,35,'2024-10-05 15:52:36.827000','xin xet duyet','PENDING'),(45,41,'2024-10-29 15:38:22.474000','Đồng ý','APPROVED'),(46,42,'2024-10-29 16:49:58.747000','xin xet duyet','PENDING'),(47,42,'2024-10-29 16:51:06.778000','từ chối','REJECTED');
/*!40000 ALTER TABLE `event_form_history` ENABLE KEYS */;
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
