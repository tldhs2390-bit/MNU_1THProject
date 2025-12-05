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
-- Table structure for table `tbl_guide`
--

DROP TABLE IF EXISTS `tbl_guide`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_guide` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `category` varchar(10) NOT NULL,
  `best_date` varchar(20) DEFAULT NULL,
  `level` varchar(7) DEFAULT NULL,
  `water` varchar(50) DEFAULT NULL,
  `medicine` varchar(50) DEFAULT NULL,
  `last_date` varchar(20) DEFAULT NULL,
  `link` varchar(400) DEFAULT NULL,
  `image_filename` varchar(50) DEFAULT NULL,
  `place` varchar(6) DEFAULT NULL,
  `image` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_guide`
--

LOCK TABLES `tbl_guide` WRITE;
/*!40000 ALTER TABLE `tbl_guide` DISABLE KEYS */;
INSERT INTO `tbl_guide` VALUES (1,'감','과일','3~4월','★★★★★','주 1200ml','NPK균형비료, 칼륨영양제','1~2년','https://blog.naver.com/sixkim1731/222481944979','gam.jpeg','실외',NULL),(2,'감자','채소','2~3월','★★★','주 700ml','인산비료, 칼륨영양제','90~110일','https://epicplants.tistory.com/entry/When-to-Plant-Fall-Potatoes-Complete-Guide-to-Regional-Timelines-Seed-Potato-Tips','gamja.jpeg','실외',NULL),(3,'고구마','채소','4~5월','★★★','주 800ml','칼륨영양제, 아미노산','100~120일','https://blog.naver.com/then14912/223816157075','goguma.jpeg','실외',NULL),(4,'고추','채소','3~4월','★★★','주 700ml','칼륨비료, 아미노산액비','70~80일','https://blog.naver.com/hanyang8156/222203088336','gochu.jpeg','실내,실외',NULL),(5,'두리안','과일','5~7월','★★★★★','주 1500ml','칼슘영양제, 칼륨영양제','1년','https://yourdurian.com/%EB%91%90%EB%A6%AC%EC%95%88-%EC%9E%AC%EB%B0%B0-%EC%A1%B0%EA%B1%B4-%ED%95%9C%EA%B5%AD%EC%97%90%EC%84%9C-%ED%82%A4%EC%9A%B0%EB%8A%94-%EB%B0%A9%EB%B2%95/','durian.jpeg','실외',NULL),(6,'딸기','과일','2~3월','★★★','주 600ml','칼륨영양제, 칼슘보조제','90~120일','https://blog.naver.com/gam8945/223802020996','redberry.jpeg','실내,실외',NULL),(8,'라벤더','허브','3~5월','★★★★','주 400ml','마그네슘영양제, 종합영양제','120~150일','https://kk7411.tistory.com/entry/%EB%B2%A0%EB%9E%80%EB%8B%A4%EC%97%90%EC%84%9C-%EB%9D%BC%EB%B2%A4%EB%8D%94-%ED%82%A4%EC%9A%B0%EB%8A%94-%EB%B0%A9%EB%B2%95-%EB%AC%BC%EC%A3%BC%EA%B8%B0-%ED%96%87%EB%B9%9B-%EC%98%A8%EB%8F%84-%ED%82%A4%EC%9A%B0%EA%B8%B0-%EB%82%9C%EC%9D%B4%EB%8F%84','rabender.jpeg','실내,실외',NULL),(9,'레몬','과일','3~5월','★★★★','주 1000ml','철분보조제, 칼슘영양제','6~12개월','https://blog.naver.com/taehyoung-kim/221690967285','lemon.jpeg','실내,실외',NULL),(10,'로즈마리','허브','3~5월','★★★','주 300ml','아미노산액비, 마그네슘','100~150일','https://blog.naver.com/minapr/222071598722','loz.jpeg','실내,실외',NULL),(11,'망고','과일','4~6월','★★★★★','주 1500ml','칼륨영양제, 아미노산','6~12개월','https://ko.jardineriaon.com/%EB%A7%9D%EA%B3%A0-%EC%8A%A4%ED%86%A4%EC%9D%84-%EC%8B%AC%EB%8A%94-%EB%B0%A9%EB%B2%95.html','mango.jpeg','실내,실외',NULL),(12,'무','채소','3~4월','★★','주 600ml','질소비료, 종합영양제','60~70일','https://blog.naver.com/triphappy/223656040102','mu.jpeg','실외',NULL),(13,'미나리','채소','2~4월','★★','주 800ml','질소위주액비, 종합영양제','60일','https://blog.naver.com/ckeorma9o/223211394200','minari.jpeg','실내,실외',NULL),(14,'배','과일','3~4월','★★★★★','주 1200ml','질소+칼륨복합, 미량요소제','1~2년','https://blog.naver.com/everfarm1414/223672204621','bea.jpeg','실외',NULL),(15,'블루베리','과일','3~4월','★★★★','주 800ml','산성전용비료, 철분보조제','1년','https://blog.naver.com/guswl2804/223969797299','blueberry.jpeg','실내,실외',NULL),(16,'상추','채소','3~5월','★★','주 500ml','NPK10-10-10, 칼슘보조제','30~40일','https://blog.naver.com/nlannhkctt57277/223976148958','sanchu.jpeg','실내,실외',NULL),(17,'생강','채소','3~4월','★★★★','주 900ml','칼륨비료, 마그네슘','150~180일','https://blog.naver.com/agridays8/223259501350','sangan.jpeg','실내,실외',NULL),(18,'얼그레이','허브','4~6월','★★★','주 500ml','칼슘보조제, 아미노산','90~120일','https://day-blossom.com/entry/%EC%96%BC%EA%B7%B8%EB%A0%88%EC%9D%B4-%ED%97%88%EB%B8%8C-%EC%9E%AC%EB%B0%B0%EC%99%80-%EA%B4%80%EB%A6%AC%EB%B2%95-%EC%99%84%EB%B2%BD-%EA%B0%80%EC%9D%B4%EB%93%9C','ul.jpeg','실내,실외',NULL),(19,'오렌지','과일','3~5월','★★★★','주 4000ml','NPK균형비료, 칼륨비료','3년~5년','https://blog.naver.com/sk3d4da/222936583718','orenge.jpeg','실내,실외',NULL),(20,'유자','과일','3~4월','★★★★','주 1000ml','칼슘보조제, NPK균형비료','180~240일','https://senior-line.com/%EC%9C%A0%EC%9E%90-%ED%82%A4%EC%9A%B0%EA%B8%B0-%EB%AC%BC%EC%A3%BC%EA%B8%B0-%EB%AA%A8%EC%A2%85-%EC%98%A8%EB%8F%84-%EC%8A%B5%EB%8F%84-%EC%88%98%EA%B2%BD%EC%9E%AC%EB%B0%B0-%ED%95%9C%EB%B0%A9/','yuja.jpeg','실내,실외',NULL),(21,'자몽','과일','4~6월','★★★★★','주 1200ml','아미노산영양제, 칼슘','1년','https://senior-line.com/%EC%9E%90%EB%AA%BD-%ED%82%A4%EC%9A%B0%EA%B8%B0-%EC%8B%AC%EB%8A%94%EB%B2%95-%EB%AA%A8%EC%A2%85-%EB%AC%BC%EC%A3%BC%EA%B8%B0-%EC%88%98%EA%B2%BD%EC%9E%AC%EB%B0%B0-%EC%B4%9D%EC%A0%95%EB%A6%AC/','jamon.jpeg','실내,실외',NULL),(22,'참다래','과일','3~4월','★★★★','주 900ml','질소영양제, 마그네슘','150~180일','https://y3850.tistory.com/1933679','kiwi.jpeg','실외',NULL),(23,'치킨','허브','1~12','★☆☆☆☆','2','2','2','https://www.tlj.co.kr:7008/microsite/2025/christmas/intro.asp','exam_chi.jpeg','실내,실외',NULL),(24,'케이크','채소','1~12','★☆☆☆☆','주 30ml','NPK 균형비료','매일','https://www.tlj.co.kr:7008/microsite/2025/christmas/intro.asp','exam_cake.jpeg','실내',NULL),(25,'콩나물','채소','1~12월','★','주 300ml','종합영양제, 아미노산소량','3~5일','https://www.culture.go.kr/mov/culturePdView.do?idx=6822','kongnamul.jpeg','실내,실외',NULL),(26,'토마토','채소','3~5월','★★★★','주 1000ml','칼슘보조제, 실리카영양제','80~90일','https://miraclespeaking.com/%EC%B4%88%EB%B3%B4%EC%9E%90%EB%8F%84-%EC%84%B1%EA%B3%B5%ED%95%98%EB%8A%94-%ED%86%A0%EB%A7%88%ED%86%A0-%ED%82%A4%EC%9A%B0%EA%B8%B0%EF%BD%9C%EC%8B%AC%EB%8A%94-%EC%8B%9C%EA%B8%B0-%EB%B0%A9%EB%B2%95/','tomato.jpeg','실내,실외',NULL),(27,'파','채소','2~4월','★★','주 500ml','질소보조제, NPK균형영양제','60~90일','https://blog.naver.com/klklkl0910/222264159374','pa.jpeg','실내,실외',NULL),(28,'파인애플','과일','4~6월','★★★★','주 1200ml','질소보조제, 칼륨영양제','12~18개월','https://ko.wikihow.com/%ED%8C%8C%EC%9D%B8%EC%95%A0%ED%94%8C-%ED%82%A4%EC%9A%B0%EB%8A%94-%EB%B0%A9%EB%B2%95','fain.jpeg','실내,실외',NULL),(29,'파파야','과일','3~5월','★★★★','주 1200ml','NPK복합비료, 아미노산','7~10개월','https://blog.naver.com/unjeofa896/223132454204','papaya.jpeg','실외',NULL),(30,'파프리카','채소','3~4월','★★★★','주 1000ml','칼륨비료, 아미노산비료','90~120일','https://blog.naver.com/manual2005/223938103696','papulica.jpeg','실내,실외',NULL),(31,'페퍼민트','허브','4~6월','★★','주 400ml','질소영양제, 종합영양제','60~80일','https://whitewolfstory.tistory.com/359','pepper.jpeg','실내,실외',NULL),(32,'호박','채소','4~6월','★★★','주 1200ml','칼륨위주영양제, 아미노산영양제','70~80일','https://nadopick.tistory.com/entry/%ED%98%B8%EB%B0%95-%ED%82%A4%EC%9A%B0%EB%8A%94-%EB%B0%A9%EB%B2%95','hobak.jpeg','실외',NULL),(33,'히비스커스','허브','4~6월','★★★','주 600ml','칼륨영양제, 종합액비','90~120일','https://seo15647.tistory.com/entry/%ED%9E%88%EB%B9%84%EC%8A%A4%EC%BB%A4%EC%8A%A4-%ED%99%94%EB%B6%84-%ED%82%A4%EC%9A%B0%EB%8A%94-%EB%B2%95%EA%B3%BC-%EA%BD%83-%ED%94%BC%EC%9A%B0%EB%8A%94-%ED%99%98%EA%B2%BD','hibi.jpeg','실내,실외',NULL),(34,'피자','채소','1~12','★★☆☆☆','주 30ml','NPK 균형비료','20분','https://web.dominos.co.kr/main?utm_campaign=2512_home_pc_home&utm_medium=cpc&utm_source=naverbs&utm_content=non_pc_home&utm_term=non','exam_pizza.jpeg','실내',NULL),(35,'떡볶이','과일','1~5','★★★☆☆','주 30ml','NPK 균형비료','12','https://www.youngdabang.com/','exam_tokk.jpeg','실외',NULL);
/*!40000 ALTER TABLE `tbl_guide` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-05  9:46:20
