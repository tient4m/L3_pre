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
-- Table structure for table `event_form`
--

DROP TABLE IF EXISTS `event_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_form` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `submission_date` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `manager_comments` varchar(255) DEFAULT NULL,
  `leader_comments` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `leader_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `event_form_ibfk_1` (`employee_id`),
  KEY `event_form_ibfk_2` (`manager_id`),
  KEY `fk_leader_01` (`leader_id`),
  CONSTRAINT `event_form_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `event_form_ibfk_2` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_leader_01` FOREIGN KEY (`leader_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_form`
--

LOCK TABLES `event_form` WRITE;
/*!40000 ALTER TABLE `event_form` DISABLE KEYS */;
INSERT INTO `event_form` VALUES (32,'REGISTRATION','2024-10-05 15:27:44.090000','2024-10-05 15:29:44.211000','Nhân viên mới IT','xin xác nhận','đồng ý','APPROVED','Nothing',8,28,11),(34,'SALARY INCREASE','2024-10-05 15:34:13.497000','2024-10-05 15:40:05.284000','Tăng lương nhân viên A','xin xet duyet','Đồng ý','APPROVED',NULL,8,28,11),(35,'SALARY INCREASE','2024-10-05 15:43:13.701000','2024-10-10 07:00:00.000000','Tăng lương nhân viên A','xin xet duyet',NULL,'PENDING',NULL,8,28,11),(38,'PROMOTION','2024-10-06 00:09:40.426000',NULL,'Thăng chức A',NULL,NULL,'DRAFT',NULL,NULL,28,11),(39,'PROPOSAL',NULL,NULL,'đề xuất kế hoạch',NULL,NULL,NULL,NULL,NULL,28,NULL),(40,'PROPOSAL',NULL,NULL,'đề xuất kế hoạch',NULL,NULL,NULL,NULL,NULL,28,NULL),(41,'REGISTRATION','2024-10-29 15:36:32.639000','2024-10-29 15:38:22.473000','Nhân viên mới IT','xin xác nhận','Đồng ý','APPROVED','Nothing',8,55,11),(42,'SALARY INCREASE','2024-10-29 16:49:36.210000','2024-10-29 16:51:06.778000','Tăng lương nhân viên A','xin xet duyet','từ chối','REJECTED',NULL,8,55,11);
/*!40000 ALTER TABLE `event_form` ENABLE KEYS */;
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
