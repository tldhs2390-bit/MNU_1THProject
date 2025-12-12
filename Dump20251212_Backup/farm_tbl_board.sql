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
-- Table structure for table `tbl_board`
--

DROP TABLE IF EXISTS `tbl_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_board` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `pass` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `regdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `subject` varchar(100) NOT NULL,
  `contents` varchar(2000) NOT NULL,
  `readcnt` int DEFAULT '0',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_board`
--

LOCK TABLES `tbl_board` WRITE;
/*!40000 ALTER TABLE `tbl_board` DISABLE KEYS */;
INSERT INTO `tbl_board` VALUES (1,'1234','관리자','2025-12-09 21:23:11','회원가입시 포인트 1000P!!','<<회원가입 이벤트>>\r\nFarmProject 오픈\r\n회원가입만 하시면 기본 1000P 증정 중입니다!!\r\n많은 회원가입 바랍니다.',6),(16,'1234','관리자','2025-12-10 09:57:06','포인트 수급 방법 공지','포인트 수급에 대한 문의가 많아 공지올립니다.\r\n포인트는 회원가입 시 기본 1000P를 제공중입니다.\r\n성장게시판의 글을 작성하시면 100P를 얻을 수 있습니다.\r\n(다만,게시글 작성 후 포인트 수급은 1일 2회로 제한되어있습니다.)\r\n포인트는 복지처에서 물건을 구입하실 때 1P당 1원으로 포인트로 금액 차감이 가능합니다.\r\n많은 이용 부탁드립니다.\r\n',4),(17,'1234','관리자','2025-12-10 10:00:54','랭킹 시스템에 대한 공지','<<랭킹 순위>>\r\n\r\n1단계 — ? 초보 농부 & 누적 포인트 1000점 이상\r\n이제 막 농장을 시작한 초보 농부\r\n\r\n\r\n2단계 — ? 아기 농부 & 누적 포인트 2000점 이상\r\n작지만 귀여운 농부\r\n\r\n\r\n3단계 — ? 들판 지기 & 누적 포인트 3000점 이상\r\n작물도 키우고 농장도 조금씩 활발해지는 중급 유저\r\n\r\n\r\n4단계 — ? 농장 주인 & 누적 포인트 4000점 이상\r\n농장이 잘 돌아가는 숙련자\r\n\r\n\r\n5단계 — ? 대농장 마스터 & 누적 포인트 5000점 이상\r\n농장 세계관 최상위 등급\r\n\r\n',9),(18,'1234','관리자','2025-12-10 10:09:10','복지처에 관한 공지','복지처는 향후 다양한 곳과 제휴를 맺을 예정입니다.',6);
/*!40000 ALTER TABLE `tbl_board` ENABLE KEYS */;
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
