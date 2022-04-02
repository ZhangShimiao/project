/*
Navicat MySQL Data Transfer

Source Server         : database
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : recipes

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2021-11-21 12:47:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `commentUserId` int(11) NOT NULL,
  `beCommentUserId` int(11) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('29', '61', '4', '0', '很好', '2021-11-20 09:38:54');
INSERT INTO `t_comment` VALUES ('30', '37', '5', '0', '你好', '2021-11-20 09:52:28');
INSERT INTO `t_comment` VALUES ('31', '68', '5', '0', '哈哈', '2021-11-20 10:06:34');
INSERT INTO `t_comment` VALUES ('32', '66', '4', '0', 'hhh', '2021-11-21 12:00:35');
