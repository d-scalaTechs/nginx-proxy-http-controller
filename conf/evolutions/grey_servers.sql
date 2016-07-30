/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-07-29 16:10:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_servers
-- ----------------------------
DROP TABLE IF EXISTS `grey_servers`;
CREATE TABLE `grey_servers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '系统名称',
  `description` varchar(300) DEFAULT NULL COMMENT '系统描述',
  `entrance` varchar(200) DEFAULT NULL COMMENT '入口/地址',
  `server_type` smallint(1) NOT NULL COMMENT '灰度系统,1:前台网站(WEB);2:运营系统(OSS)',
  `sub_system_id` varchar(200) NOT NULL COMMENT '系统名称',
  `status` smallint(1) NOT NULL COMMENT '是否启用,0:禁用(DISABLE);1:启用(ENABLE)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='灰度';

-- ----------------------------
-- Records of grey_servers
-- ----------------------------
INSERT INTO `grey_servers` VALUES ('1', 'sandbox1-oss', 'sandbox1   oss order', '127.0.0.1:8081', '2', '1', '1');
INSERT INTO `grey_servers` VALUES ('2', 'sandbox1-oss', 'sandbox1   oss crm', '127.0.0.1:8082', '2', '2', '1');
INSERT INTO `grey_servers` VALUES ('3', 'sandbox1-oss', 'sandbox1   oss admin', '127.0.0.1:8083', '2', '3', '1');
INSERT INTO `grey_servers` VALUES ('4', 'sandbox2-web', 'sandbox2   web www', '192.168.0.2:8081', '1', '4', '1');
INSERT INTO `grey_servers` VALUES ('5', 'sandbox2-web', 'sandbox2   web pai', '192.168.0.2:8082', '1', '5', '1');
INSERT INTO `grey_servers` VALUES ('6', 'sandbox2-web', 'sandbox2   web etrade', '192.168.0.2:8083', '1', '6', '1');
