/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:47:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `followId` int(11) NOT NULL,
  `beFollowId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_follow
-- ----------------------------
INSERT INTO `t_follow` VALUES ('3', '5', '4');
