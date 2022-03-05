/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:48:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_scan
-- ----------------------------
DROP TABLE IF EXISTS `t_scan`;
CREATE TABLE `t_scan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_scan
-- ----------------------------
INSERT INTO `t_scan` VALUES ('2', '4', '5');
INSERT INTO `t_scan` VALUES ('3', '5', '5');
INSERT INTO `t_scan` VALUES ('4', '51', '5');
INSERT INTO `t_scan` VALUES ('5', '67', '5');
INSERT INTO `t_scan` VALUES ('6', '66', '5');
INSERT INTO `t_scan` VALUES ('7', '60', '5');
INSERT INTO `t_scan` VALUES ('8', '58', '5');
INSERT INTO `t_scan` VALUES ('9', '59', '5');
INSERT INTO `t_scan` VALUES ('10', '35', '4');
INSERT INTO `t_scan` VALUES ('11', '61', '4');
INSERT INTO `t_scan` VALUES ('12', '68', '5');
INSERT INTO `t_scan` VALUES ('13', '37', '5');
INSERT INTO `t_scan` VALUES ('14', '66', '4');
INSERT INTO `t_scan` VALUES ('15', '64', '4');
