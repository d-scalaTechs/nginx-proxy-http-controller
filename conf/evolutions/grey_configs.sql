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
DROP TABLE IF EXISTS `grey_configs`;
CREATE TABLE `grey_configs` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(20) NOT NULL COMMENT 'staffName, userName, (userId, ip ...)',
  `value` varchar(100) NOT NULL COMMENT '值',
  `server_id` int(10) NOT NULL COMMENT '灰度服务id',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of grey_configs
-- ----------------------------
INSERT INTO `grey_configs` VALUES ('2', 'staffName', 'login1', '3', '2016-07-29 15:58:45');
INSERT INTO `grey_configs` VALUES ('3', 'staffName', 'login2', '2', '2016-07-29 15:58:49');
INSERT INTO `grey_configs` VALUES ('4', 'staffName', 'login3', '4', '2016-07-29 15:58:53');
INSERT INTO `grey_configs` VALUES ('15', 'staffName', 'login4', '1', '2016-07-29 16:10:18');
INSERT INTO `grey_configs` VALUES ('16', 'staffName', 'login5', '1', '2016-07-29 15:59:02');
INSERT INTO `grey_configs` VALUES ('18', 'staffName', 'login6', '3', '2016-07-29 15:59:29');
INSERT INTO `grey_configs` VALUES ('19', 'staffName', 'login7', '3', '2016-07-29 15:59:14');
INSERT INTO `grey_configs` VALUES ('20', 'staffName', 'login8', '1', '2016-07-29 16:10:20');
INSERT INTO `grey_configs` VALUES ('21', 'staffName', 'login9', '3', '2016-07-29 16:10:19');
INSERT INTO `grey_configs` VALUES ('23', 'staffName', 'login10', '6', '2016-07-29 16:10:21');
INSERT INTO `grey_configs` VALUES ('24', 'staffName', 'login11', '1', '2016-07-29 16:10:22');
