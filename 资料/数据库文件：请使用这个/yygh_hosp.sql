/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.16 : Database - yygh_hosp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yygh_hosp` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `yygh_hosp`;

/*Table structure for table `hospital_set` */

DROP TABLE IF EXISTS `hospital_set`;

CREATE TABLE `hospital_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hosname` varchar(100) DEFAULT NULL COMMENT '医院名称',
  `hoscode` varchar(30) DEFAULT NULL COMMENT '医院编号',
  `api_url` varchar(100) DEFAULT NULL COMMENT 'api基础路径',
  `sign_key` varchar(50) DEFAULT NULL COMMENT '签名秘钥',
  `contacts_name` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contacts_phone` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hoscode` (`hoscode`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='医院设置表';

/*Data for the table `hospital_set` */

insert  into `hospital_set`(`id`,`hosname`,`hoscode`,`api_url`,`sign_key`,`contacts_name`,`contacts_phone`,`status`,`create_time`,`update_time`,`is_deleted`) values (1,'北大国际医院','1000_0','http://localhost:9998/','674c4139707728439a6510eae12deea9','赵琳','120',1,'2020-12-29 10:41:50','2021-01-13 13:16:17',0),(2,'北京妇产医院','1000_3','http://localhost:9998/','162662436783fd8807931d1ff021c444','光头强','114',1,'2020-12-29 10:42:14','2021-01-13 13:16:18',0),(3,'北京地坛医院','1000_4','http://localhost:9998/','162662436783fd8807931d1ff0299999','熊大','122',1,'2020-12-30 10:33:48','2021-01-13 13:16:19',0),(6,'北京协和医院1','1000_01','http://localhost:9998/1','674c4139707728439a6510eae12deea9','李四1','1209991',1,'2020-12-09 09:33:46','2020-12-29 09:49:45',0),(8,'中日友好医院','1000_1','http://localhost:9999','162662436783fd8807931d1ff021c18b','张三','110119',0,'2020-12-22 14:56:00','2020-12-30 13:52:08',0),(9,'北京儿童医院','1000_5','localhost','e9649b7a597025b1df64c475c91dcaeb','熊二','119',1,'2020-12-30 15:23:07','2020-12-30 15:23:07',0),(10,'411','4','4','c8c0e8f10912396ed1054200200ff1b0','4','4',1,'2020-12-30 16:06:22','2021-01-29 09:20:49',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
