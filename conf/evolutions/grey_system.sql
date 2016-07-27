/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-07-22 16:13:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_system
-- ----------------------------
DROP TABLE IF EXISTS `grey_system`;
CREATE TABLE `grey_system` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '系统名称',
  `description` varchar(300) DEFAULT NULL COMMENT '系统描述',
  `entrance` varchar(200) DEFAULT NULL COMMENT '入口/地址',
  `system_type` smallint(1) NOT NULL COMMENT '灰度系统,1:前台网站(WEB);2:运营系统(OSS)',
  `sub_system` VARCHAR(200) NOT NULL COMMENT '系统名称',
  `system_type` smallint(1) NOT NULL COMMENT '是否启用,0:禁用(DISABLE);1:启用(ENABLE)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='灰度';

-- ----------------------------
-- Records of grey_system
-- ----------------------------
INSERT INTO `grey_system` VALUES ('1', 'sandbox1-oss', 'sandbox1   oss order', '127.0.0.1','1','order');
INSERT INTO `grey_system` VALUES ('2', 'sandbox1-oss', 'sandbox1   oss crm', '127.0.0.1','1','crm');
INSERT INTO `grey_system` VALUES ('2', 'sandbox1-oss', 'sandbox1   oss admin', '127.0.0.1','1','admin');
INSERT INTO `grey_system` VALUES ('3', 'sandbox2-web', 'sandbox2   web www', '127.0.0.1','2','www');
INSERT INTO `grey_system` VALUES ('4', 'sandbox2-web', 'sandbox2   web pai', '127.0.0.1','2','pai');
INSERT INTO `grey_system` VALUES ('4', 'sandbox2-web', 'sandbox2   web etrade', '127.0.0.1','2','etrade');
