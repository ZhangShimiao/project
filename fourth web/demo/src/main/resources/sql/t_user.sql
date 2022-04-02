/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:48:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `type` int(11) NOT NULL,
  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `headimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '超级管理员', 'admin', '2', null, null, null);
INSERT INTO `t_user` VALUES ('2', 'adminA', '管理员A', '111', '1', null, null, null);
INSERT INTO `t_user` VALUES ('4', '111', '111', '111', '0', '1', '111', null);
INSERT INTO `t_user` VALUES ('5', '222', '222', '111', '0', '2', '222', '/recipesfiles/16373730726535bb334a1eafd4269a9ce004c98e033bf.png');
