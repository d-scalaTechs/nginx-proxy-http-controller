/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : gray

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-08-01 14:02:10
*/

use gray;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_configs
-- ----------------------------
DROP TABLE IF EXISTS `grey_configs`;
CREATE TABLE `grey_configs` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(20) NOT NULL COMMENT 'staffName, userName, (userId, ip ...)',
  `value` varchar(100) NOT NULL COMMENT '值',
  `server_id` int(10) NOT NULL COMMENT '灰度服务id',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of grey_configs
-- ----------------------------
INSERT INTO `grey_configs` VALUES ('1', 'staffName', 'login1', '3', '2016-07-29 15:58:45');
INSERT INTO `grey_configs` VALUES ('2', 'staffName', 'login2', '2', '2016-07-29 15:58:49');
INSERT INTO `grey_configs` VALUES ('3', 'staffName', 'login3', '4', '2016-07-29 15:58:53');
INSERT INTO `grey_configs` VALUES ('4', 'staffName', 'login4', '1', '2016-07-29 16:10:18');
INSERT INTO `grey_configs` VALUES ('5', 'staffName', 'login5', '1', '2016-07-29 15:59:02');
INSERT INTO `grey_configs` VALUES ('6', 'staffName', 'login6', '3', '2016-07-29 15:59:29');
INSERT INTO `grey_configs` VALUES ('8', 'staffName', 'login8', '1', '2016-07-29 16:10:20');
INSERT INTO `grey_configs` VALUES ('9', 'staffName', 'login9', '3', '2016-07-29 16:10:19');
INSERT INTO `grey_configs` VALUES ('10', 'staffName', 'login10', '6', '2016-07-29 16:10:21');
INSERT INTO `grey_configs` VALUES ('11', 'staffName', 'login11', '1', '2016-07-29 16:10:22');

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

-- ----------------------------
-- Table structure for sub_systems
-- ----------------------------
DROP TABLE IF EXISTS `sub_systems`;
CREATE TABLE `sub_systems` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of sub_systems
-- ----------------------------
INSERT INTO `sub_systems` VALUES ('1', 'order');
INSERT INTO `sub_systems` VALUES ('2', 'crm');
INSERT INTO `sub_systems` VALUES ('3', 'admin');
INSERT INTO `sub_systems` VALUES ('4', 'www');
INSERT INTO `sub_systems` VALUES ('5', 'pai');
INSERT INTO `sub_systems` VALUES ('6', 'etrade');

/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : gray

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-08-01 14:02:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_configs
-- ----------------------------
DROP TABLE IF EXISTS `grey_configs`;
CREATE TABLE `grey_configs` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(20) NOT NULL COMMENT 'staffName, userName, (userId, ip ...)',
  `value` varchar(100) NOT NULL COMMENT '值',
  `server_id` int(10) NOT NULL COMMENT '灰度服务id',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of grey_configs
-- ----------------------------
INSERT INTO `grey_configs` VALUES ('1', 'staffName', 'login1', '3', '2016-07-29 15:58:45');
INSERT INTO `grey_configs` VALUES ('2', 'staffName', 'login2', '2', '2016-07-29 15:58:49');
INSERT INTO `grey_configs` VALUES ('3', 'staffName', 'login3', '4', '2016-07-29 15:58:53');
INSERT INTO `grey_configs` VALUES ('4', 'staffName', 'login4', '1', '2016-07-29 16:10:18');
INSERT INTO `grey_configs` VALUES ('5', 'staffName', 'login5', '1', '2016-07-29 15:59:02');
INSERT INTO `grey_configs` VALUES ('6', 'staffName', 'login6', '3', '2016-07-29 15:59:29');
INSERT INTO `grey_configs` VALUES ('8', 'staffName', 'login8', '1', '2016-07-29 16:10:20');
INSERT INTO `grey_configs` VALUES ('9', 'staffName', 'login9', '3', '2016-07-29 16:10:19');
INSERT INTO `grey_configs` VALUES ('10', 'staffName', 'login10', '6', '2016-07-29 16:10:21');
INSERT INTO `grey_configs` VALUES ('11', 'staffName', 'login11', '1', '2016-07-29 16:10:22');

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

-- ----------------------------
-- Table structure for sub_systems
-- ----------------------------
DROP TABLE IF EXISTS `sub_systems`;
CREATE TABLE `sub_systems` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of sub_systems
-- ----------------------------
INSERT INTO `sub_systems` VALUES ('1', 'order');
INSERT INTO `sub_systems` VALUES ('2', 'crm');
INSERT INTO `sub_systems` VALUES ('3', 'admin');
INSERT INTO `sub_systems` VALUES ('4', 'www');
INSERT INTO `sub_systems` VALUES ('5', 'pai');
INSERT INTO `sub_systems` VALUES ('6', 'etrade');