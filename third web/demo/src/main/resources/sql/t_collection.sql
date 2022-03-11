/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:46:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_collection
-- ----------------------------
DROP TABLE IF EXISTS `t_collection`;
CREATE TABLE `t_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_collection
-- ----------------------------
INSERT INTO `t_collection` VALUES ('2', '5', '5');
INSERT INTO `t_collection` VALUES ('3', '67', '5');
INSERT INTO `t_collection` VALUES ('4', '66', '5');
INSERT INTO `t_collection` VALUES ('5', '35', '4');
INSERT INTO `t_collection` VALUES ('6', '61', '4');
INSERT INTO `t_collection` VALUES ('7', '37', '5');
INSERT INTO `t_collection` VALUES ('8', '66', '4');
