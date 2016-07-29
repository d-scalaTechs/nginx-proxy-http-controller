/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-07-29 16:10:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_configs
-- ----------------------------
DROP TABLE IF EXISTS `sub_systems`;
CREATE TABLE `sub_systems` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='灰度子系统';

-- ----------------------------
-- Records of grey_configs
-- ----------------------------
INSERT INTO `sub_systems` VALUES ('1', 'order');
INSERT INTO `sub_systems` VALUES ('2', 'crm');
INSERT INTO `sub_systems` VALUES ('3', 'admin');
INSERT INTO `sub_systems` VALUES ('4', 'www');
INSERT INTO `sub_systems` VALUES ('5', 'pai');
INSERT INTO `sub_systems` VALUES ('6', 'etrade');
