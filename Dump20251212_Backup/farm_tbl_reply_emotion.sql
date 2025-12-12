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
-- Table structure for table `tbl_reply_emotion`
--

DROP TABLE IF EXISTS `tbl_reply_emotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_reply_emotion` (
  `r_idx` int NOT NULL,
  `user_idx` int NOT NULL,
  `type` varchar(10) NOT NULL,
  `regdate` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`r_idx`,`user_idx`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_reply_emotion`
--

LOCK TABLES `tbl_reply_emotion` WRITE;
/*!40000 ALTER TABLE `tbl_reply_emotion` DISABLE KEYS */;
INSERT INTO `tbl_reply_emotion` VALUES (8,7,'like','2025-12-11 10:11:39'),(12,7,'like','2025-12-11 10:17:56'),(13,13,'like','2025-12-11 18:27:55'),(14,6,'like','2025-12-11 15:21:00'),(14,7,'like','2025-12-11 15:20:35'),(14,13,'like','2025-12-11 17:45:59'),(15,6,'like','2025-12-11 15:26:59'),(16,7,'like','2025-12-11 15:35:46'),(17,7,'like','2025-12-11 15:38:45'),(18,7,'like','2025-12-11 15:53:29'),(19,7,'like','2025-12-11 16:03:55'),(19,7,'sad','2025-12-11 16:03:57'),(19,7,'sym','2025-12-11 16:03:56');
/*!40000 ALTER TABLE `tbl_reply_emotion` ENABLE KEYS */;
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
