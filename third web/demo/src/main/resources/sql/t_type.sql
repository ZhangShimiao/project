/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:48:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '川菜');
INSERT INTO `t_type` VALUES ('2', '东北菜');
INSERT INTO `t_type` VALUES ('3', '韩式');
INSERT INTO `t_type` VALUES ('4', '泰式');
INSERT INTO `t_type` VALUES ('5', '西餐');
INSERT INTO `t_type` VALUES ('6', '减肥');
INSERT INTO `t_type` VALUES ('7', '家常菜');
INSERT INTO `t_type` VALUES ('8', '烘焙');
