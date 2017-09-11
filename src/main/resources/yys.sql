/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.11 : Database - yys
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yys` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `yys`;

/*Table structure for table `tb_gkey` */

DROP TABLE IF EXISTS `tb_gkey`;

CREATE TABLE `tb_gkey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `a` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_gkey` */

/*Table structure for table `weight` */

DROP TABLE IF EXISTS `weight`;

CREATE TABLE `weight` (
  `id` varchar(45) NOT NULL,
  `weight` decimal(10,4) DEFAULT NULL,
  `tracknbr` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `weight` */

insert  into `weight`(`id`,`weight`,`tracknbr`,`create_date`,`modify_date`) values ('0c5f721b-3010-41ec-8d15-ca5bcbe0c660','1.2300','TEST1','2017-07-12 15:25:10','2017-07-12 15:25:35'),('476bb978-3485-4bc4-bf2d-9f1ae8dfeb87','34.0000','2','2017-07-12 16:13:22','2017-07-12 16:13:24'),('5cb3872d-c6de-4812-b363-a200c246c6f0','1.0000','1','2017-07-12 16:13:19','2017-07-12 16:13:19'),('a8772b8f-ad3c-4c0c-8ad8-adef06d88dea','3.0000','3','2017-07-12 16:13:27','2017-07-12 16:13:27'),('cd5d189e-9223-4354-b580-795c3b7a3251','5.0000','4','2017-07-12 16:13:29','2017-07-12 16:13:29');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
