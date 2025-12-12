-- MySQL dump 10.13  Distrib 8.0.43, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: farm
-- ------------------------------------------------------
-- Server version	8.4.6

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
-- Table structure for table `tbl_reply`
--

DROP TABLE IF EXISTS `tbl_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_reply` (
  `r_idx` int NOT NULL AUTO_INCREMENT,
  `post_idx` int NOT NULL,
  `parent` int DEFAULT '0',
  `n_name` varchar(20) DEFAULT NULL,
  `contents` varchar(500) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `emoji` varchar(50) DEFAULT NULL,
  `regdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `like_cnt` int DEFAULT '0',
  `sym_cnt` int DEFAULT '0',
  `sad_cnt` int DEFAULT '0',
  `status` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`r_idx`),
  KEY `post_idx` (`post_idx`),
  KEY `n_name` (`n_name`),
  CONSTRAINT `tbl_reply_ibfk_1` FOREIGN KEY (`post_idx`) REFERENCES `tbl_growth` (`idx`),
  CONSTRAINT `tbl_reply_ibfk_2` FOREIGN KEY (`n_name`) REFERENCES `tbl_user` (`n_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reply`
--

LOCK TABLES `tbl_reply` WRITE;
/*!40000 ALTER TABLE `tbl_reply` DISABLE KEYS */;
INSERT INTO `tbl_reply` VALUES (12,29,0,'ad','축하해요\r\n',NULL,NULL,'2025-12-11 01:17:55',1,0,0,'1'),(13,50,0,'ad','우와 잘 자랐네요',NULL,NULL,'2025-12-11 02:28:48',1,0,0,'1'),(15,28,0,'아무무','12',NULL,NULL,'2025-12-11 06:26:57',1,0,0,'1'),(16,25,0,'ad','1234',NULL,NULL,'2025-12-11 06:35:44',1,0,0,'1'),(17,26,0,'ad','12',NULL,NULL,'2025-12-11 06:38:35',1,0,0,'1'),(18,30,0,'ad','12',NULL,NULL,'2025-12-11 06:53:27',1,0,0,'1'),(19,20,0,'ad','12',NULL,NULL,'2025-12-11 07:03:54',1,1,1,'1'),(20,51,0,'admin1','잘 자랐네요',NULL,NULL,'2025-12-11 08:46:09',0,0,0,'1');
/*!40000 ALTER TABLE `tbl_reply` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12 11:14:33
