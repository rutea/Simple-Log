/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : history

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-06-26 15:17:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `live`
-- ----------------------------
DROP TABLE IF EXISTS `live`;
CREATE TABLE `live` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `log` varchar(3000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;