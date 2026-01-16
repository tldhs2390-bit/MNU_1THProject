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
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL,
  `n_name` varchar(10) NOT NULL,
  `tel` varchar(13) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `user_pass` varchar(100) DEFAULT NULL,
  `user_rank` varchar(10) DEFAULT NULL,
  `point` decimal(10,0) DEFAULT '1000',
  `regdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `today_point` int DEFAULT '0',
  `last_point` date DEFAULT NULL,
  `last_point_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idx`),
  UNIQUE KEY `n_name_UNIQUE` (`n_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'관리자','관리자','010-1111-0001','admin@test.com','서울특별시 강남구','admin01','1234','경험자',4900,'2025-12-10 08:04:59',NULL,NULL,'2025-12-11 10:53:07'),(2,'김초록','초록손','010-2222-0002','green@test.com','부산광역시 해운대구','green01','1234','미경험자',1500,'2025-12-10 08:04:59',NULL,NULL,'2025-12-11 10:53:07'),(3,'유마루','마루','010-3333-0003','maru@test.com','대구광역시 달서구','maru01','1234','경험자',1800,'2025-12-10 08:04:59',NULL,NULL,'2025-12-11 10:53:07'),(4,'이다온','다온','010-4444-0004','daon@test.com','광주광역시 북구','daon01','1234','미경험자',1700,'2025-12-10 08:04:59',NULL,NULL,'2025-12-11 10:53:07'),(5,'최세현','세현','010-5555-0005','sehyeon@test.com','인천광역시 연수구','sehyeon01','1234','경험자',1600,'2025-12-10 08:04:59',NULL,NULL,'2025-12-11 10:53:07'),(6,'김시온','아무무','010-1111-2222','tldhs2120@naver.com','주택','7sseon','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',NULL,1200,'2025-12-10 08:07:59',NULL,NULL,'2025-12-11 10:53:07'),(7,'김시온','ad','010-1111-2222','tldhs2120@naver.com',NULL,'admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',NULL,2000,'2025-12-10 09:12:59',NULL,NULL,'2025-12-11 00:00:00'),(11,'김유저','User1','010-1113-1112','tldhs2120@naver.com',NULL,'admin2','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',NULL,1100,'2025-12-11 07:11:01',100,NULL,'2025-12-11 00:00:00'),(13,'김유저','admin1','010-1111-1112','snpr218@gmail.com',NULL,'admin1','a6c81f4d8be7412ac577c11878394919b676b1ec18759becce1e1fbaf7495ebc',NULL,5000,'2025-12-11 08:02:31',200,NULL,'2025-12-11 00:00:00');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
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
