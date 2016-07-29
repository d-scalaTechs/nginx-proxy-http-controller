/*
Navicat MySQL Data Transfer

Source Server         : iplasttest
Source Server Version : 50631
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2016-07-29 11:01:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for grey_config
-- ----------------------------
DROP TABLE IF EXISTS `grey_config`;
CREATE TABLE `grey_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(20) NOT NULL COMMENT 'staffName, userName, (userId, ip ...)',
  `value` varchar(100) NOT NULL COMMENT '值',
  `server_id` int(10) NOT NULL COMMENT '灰度服务id',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='灰度配置表';

-- ----------------------------
-- Records of grey_config
-- ----------------------------
INSERT INTO `grey_config` VALUES ('2', 'staffName', '韩雷1', '3', '2016-07-22 15:03:34');
INSERT INTO `grey_config` VALUES ('3', 'staffName', '韩雷2', '2', '2016-07-22 15:03:35');
INSERT INTO `grey_config` VALUES ('4', 'staffName', '韩雷3', '4', '2016-07-22 15:03:40');
INSERT INTO `grey_config` VALUES ('15', 'ky', 'ky1', '1', '2016-07-28 14:48:39');
INSERT INTO `grey_config` VALUES ('16', 'staffName', 'ky3', '1', '2016-07-28 18:35:19');
INSERT INTO `grey_config` VALUES ('18', 'staffName', 'hanlei1', '3', '2016-07-28 16:19:37');
INSERT INTO `grey_config` VALUES ('19', 'staffName', 'hanlei2', '3', '2016-07-28 16:19:52');
INSERT INTO `grey_config` VALUES ('20', 'loginName', 'ky2', '1', '2016-07-28 18:35:26');
INSERT INTO `grey_config` VALUES ('21', 'ky', 'kyky111', '3', '2016-07-28 17:46:23');
INSERT INTO `grey_config` VALUES ('23', 'value1', 'valu1', '6', '2016-07-28 18:10:00');
