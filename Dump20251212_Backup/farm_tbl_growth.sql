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
-- Table structure for table `tbl_growth`
--

DROP TABLE IF EXISTS `tbl_growth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_growth` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `category` varchar(20) NOT NULL,
  `subject` varchar(200) NOT NULL,
  `contents` longtext NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `hashtags` varchar(200) DEFAULT NULL,
  `n_name` varchar(20) DEFAULT NULL,
  `regdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `readcnt` int DEFAULT '0',
  `like_cnt` int DEFAULT '0',
  `sym_cnt` int DEFAULT '0',
  `sad_cnt` int DEFAULT '0',
  `status` tinyint DEFAULT '1',
  `pass` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `n_name` (`n_name`),
  CONSTRAINT `tbl_growth_ibfk_1` FOREIGN KEY (`n_name`) REFERENCES `tbl_user` (`n_name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_growth`
--

LOCK TABLES `tbl_growth` WRITE;
/*!40000 ALTER TABLE `tbl_growth` DISABLE KEYS */;
INSERT INTO `tbl_growth` VALUES (1,'vegetable','베란다 상추 첫 수확','베란다에서 기른 상추가 무성하게 자라 샐러드로 먹을 만큼 풍성하게 자랐다.','veg/lettuce.jpg','#상추,#채소,#성공','관리자','2025-12-10 08:05:36',1,0,0,0,1,'1'),(2,'vegetable','고추 재배 실패','꽃은 많이 피었지만 환기 부족으로 꽃이 모두 떨어졌다.','veg/chili.jpg','#고추,#채소,#실패','초록손','2025-12-10 08:05:36',0,0,0,0,1,'1'),(3,'vegetable','미나리 폭풍성장','물을 좋아하는 미나리 특성대로 잘 자라 일주일 만에 폭풍 성장했다.','veg/minari.jpg','#미나리,#채소,#성공','마루','2025-12-10 08:05:36',0,0,0,0,1,'1'),(4,'vegetable','토마토 줄기 말림 실패','물을 너무 자주 준 탓에 뿌리가 약해지고 줄기가 말렸다.','veg/tomato.jpg','#토마토,#채소,#실패','다온','2025-12-10 08:05:36',0,0,0,0,1,'1'),(5,'vegetable','파프리카 첫 열매 성공','햇빛 좋은 창가에서 꾸준히 관리해 파프리카가 두 개나 열렸다.','veg/paprika.jpg','#파프리카,#채소,#성공','세현','2025-12-10 08:05:36',0,0,0,0,1,'1'),(6,'vegetable','콩나물 실패','물을 제때 갈아주지 않아 냄새가 나고 곰팡이가 생겼다.','veg/beansprout.jpg','#콩나물,#채소,#실패','관리자','2025-12-10 08:05:36',0,0,0,0,1,'1'),(7,'vegetable','대파 물꽂이 성공','마트 대파의 뿌리를 심었더니 새 파가 쑥쑥 올라왔다.','veg/greenonion.jpg','#대파,#채소,#성공','초록손','2025-12-10 08:05:36',0,0,0,0,1,'1'),(8,'vegetable','호박 열매 부족','잎은 무성했지만 수분이 부족해 열매가 거의 자라지 않았다.','veg/pumpkin.jpg','#호박,#채소,#실패','마루','2025-12-10 08:05:36',0,0,0,0,1,'1'),(9,'vegetable','감자 풍성한 수확','텃밭에서 키운 감자가 생각보다 크고 많이 나왔다.','veg/potato.jpg','#감자,#채소,#성공','다온','2025-12-10 08:05:36',0,0,0,0,1,'1'),(10,'vegetable','고구마 덩굴만 무성','덩굴만 길게 자라고 고구마는 거의 자라지 못했다.','veg/sweetpotato.jpg','#고구마,#채소,#실패','세현','2025-12-10 08:05:36',0,0,0,0,1,'1'),(11,'vegetable','생강 뿌리 잘 자람','따뜻한 곳에 둔 생강이 알차게 잘 자랐다.','veg/ginger.jpg','#생강,#채소,#성공','관리자','2025-12-10 08:05:36',0,0,0,0,1,'1'),(12,'vegetable','무 재배 실패','벌레 피해가 심해 잎이 뜯기고 뿌리가 자라지 않았다.','veg/radish.jpg','#무,#채소,#실패','초록손','2025-12-10 08:05:36',0,0,0,0,1,'1'),(13,'herb','히비스커스 꽃 개화 성공','붉은 히비스커스 꽃이 예쁘게 피었다.','herb/hibiscus.jpg','#히비스커스,#허브,#성공','마루','2025-12-10 08:05:36',3,0,0,0,1,'1'),(14,'herb','로즈마리 과습 실패','물을 너무 자주 줘 뿌리가 썩으며 고사했다.','herb/rosemary.jpg','#로즈마리,#허브,#실패','다온','2025-12-10 08:05:36',0,0,0,0,1,'1'),(15,'herb','페퍼민트 번식력 대단','런너가 계속 뻗으며 화분을 가득 채웠다.','herb/peppermint.jpg','#페퍼민트,#허브,#성공','세현','2025-12-10 08:05:36',0,0,0,0,1,'1'),(16,'herb','얼그레이 허브 실패','햇빛 부족으로 잎 색이 연해지고 성장이 멈추었다.','herb/earlgrey.jpg','#얼그레이,#허브,#실패','관리자','2025-12-10 08:05:36',0,0,0,0,1,'1'),(17,'herb','라벤더 향기 가득','보라색 꽃이 피고 향이 은은하게 퍼졌다.','herb/lavender.jpg','#라벤더,#허브,#성공','초록손','2025-12-10 08:05:36',0,0,0,0,1,'1'),(18,'과일','블루베리 열매 없음','산성토 준비 부족으로 열매가 거의 열리지 않았다.','fruit/blueberry.jpg','#블루베리,#과일,#실패','마루','2025-12-10 08:05:36',2,0,0,0,1,'1'),(19,'fruit','딸기 첫 수확 성공','양액 관리로 달콤한 딸기를 여러 개 수확했다.','fruit/strawberry.jpg','#딸기,#과일,#성공','다온','2025-12-10 08:05:36',0,0,0,0,1,'1'),(20,'fruit','감 나무 낙과 문제','비바람으로 어린 감이 대부분 떨어졌다.','fruit/persimmon.jpg','#감,#과일,#실패','세현','2025-12-10 08:05:36',1,1,1,1,1,'1'),(21,'fruit','배 향기 좋은 수확','달콤하고 향기로운 배를 수확했다.','fruit/pear.jpg','#배,#과일,#성공','관리자','2025-12-10 08:05:36',1,1,0,0,1,'1'),(22,'fruit','망고 실내 재배 실패','실내 온도가 맞지 않아 잎이 노랗게 변했다.','fruit/mango.jpg','#망고,#과일,#실패','초록손','2025-12-10 08:05:36',1,1,0,0,1,'1'),(23,'fruit','파인애플 잎 번식 성공','잎을 심었더니 새로운 잎이 돋아 건강하게 자랐다.','fruit/pineapple.jpg','#파인애플,#과일,#성공','마루','2025-12-10 08:05:36',1,1,0,0,1,'1'),(24,'fruit','파파야 냉해 피해','밤 온도가 낮아지며 잎이 말라 떨어졌다.','fruit/papaya.jpg','#파파야,#과일,#실패','다온','2025-12-10 08:05:36',1,1,0,0,1,'1'),(25,'fruit','두리안 화분 성공','실내에서 건강하게 자라며 잎이 빛났다.','fruit/durian.jpg','#두리안,#과일,#성공','세현','2025-12-10 08:05:36',1,1,0,0,1,'1'),(26,'fruit','참다래 잎 타버림','강한 햇빛으로 잎 일부가 타버렸다.','fruit/kiwi.jpg','#참다래,#과일,#실패','관리자','2025-12-10 08:05:36',1,1,0,0,1,'1'),(27,'fruit','자몽 향기 좋은 꽃','작은 나무에서 향기로운 하얀 꽃이 피었다.','fruit/grapefruit.jpg','#자몽,#과일,#성공','초록손','2025-12-10 08:05:36',1,1,0,0,1,'1'),(28,'vegetable','유자 냉해 실패','찬바람으로 잎이 떨어지고 나무가 약해졌다.','fruit/yuzu.jpg','#유자,#과일,#실패','마루','2025-12-10 08:05:36',3,1,0,0,1,'1'),(29,'vegetable','레몬 첫 열매 성공','작은 화분이었는데 레몬 1개 열리는데 성공.','fruit/lemon.jpg','#레몬,#과일,#성공','다온','2025-12-10 08:05:36',3,1,0,0,1,'1'),(30,'fruit','오렌지 진딧물 문제','진딧물이 잎 뒷면에 번식해 성장이 멈추었다.','fruit/orange.jpg','#오렌지,#과일,#실패','세현','2025-12-10 08:05:36',9,2,0,0,1,'1'),(50,'fruit','레몬 나무 키우기','레몬이 잘 자라요','6f60713a-dbf6-4dc9-9f98-b50b0a27b759_lemon.jpeg','#레몬,#과일,#성공','ad','2025-12-11 02:28:05',3,1,0,0,1,'1234'),(51,'fruit','레몬 나무 키우기','레몬 나무 키우기 ','9990ac61-3fd9-4ca6-a1e7-95d4222dc06f_lemon.jpeg','#레몬,#과일,#성공','ad','2025-12-11 06:20:22',5,3,0,0,1,'1234');
/*!40000 ALTER TABLE `tbl_growth` ENABLE KEYS */;
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
