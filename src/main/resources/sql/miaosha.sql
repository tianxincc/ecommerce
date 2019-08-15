/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50703
 Source Host           : localhost:3306
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50703
 File Encoding         : 65001

 Date: 15/08/2019 21:36:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `price` double(255, 2) NULL DEFAULT 0.00,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `sales` int(11) NULL DEFAULT 0,
  `img_Url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (27, 'iphone6', 8000.00, '三代手机', 102, 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4016674015,960487752&fm=27&gp=0.jpg');
INSERT INTO `item` VALUES (28, 'iphone4', 5000.00, '一代手机', 97, 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4016674015,960487752&fm=27&gp=0.jpg');
INSERT INTO `item` VALUES (29, 'iphone5', 6000.00, '二代手机', 80, 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4016674015,960487752&fm=27&gp=0.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NULL DEFAULT 0,
  `item_id` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (24, 4, 27);
INSERT INTO `item_stock` VALUES (25, 6, 28);
INSERT INTO `item_stock` VALUES (26, 1, 29);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL COMMENT 'i',
  `item_id` int(11) NULL DEFAULT NULL,
  `item_price` double(10, 2) NULL DEFAULT NULL,
  `amount` int(255) NULL DEFAULT NULL,
  `order_price` double(10, 2) NULL DEFAULT NULL,
  `promo_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019071700000000', 5, 28, NULL, 1, NULL, 0);
INSERT INTO `order_info` VALUES ('2019071700000100', 5, 28, NULL, 1, NULL, 0);
INSERT INTO `order_info` VALUES ('2019071700000200', 5, 28, NULL, 1, NULL, 0);
INSERT INTO `order_info` VALUES ('2019071700000300', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019071700000400', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019071700000500', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019071700000600', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019080500000700', 5, 27, 6.00, 1, 6.00, 0);
INSERT INTO `order_info` VALUES ('2019080500000800', 5, 27, 6.00, 1, 6.00, 0);
INSERT INTO `order_info` VALUES ('2019080500000900', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019080500001000', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019081500001100', 5, 28, 5000.00, 1, 5000.00, 0);
INSERT INTO `order_info` VALUES ('2019081500001200', 5, 28, 100.00, 1, 5000.00, 1);

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `start_date` datetime(0) NULL DEFAULT NULL,
  `item_id` int(11) NULL DEFAULT NULL,
  `promo_item_price` double(10, 2) NULL DEFAULT NULL,
  `end_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (1, 'iphone4抢购', '2019-08-15 20:28:30', 28, 100.00, '2019-09-06 22:24:50');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `current_value` int(255) NULL DEFAULT NULL,
  `stop` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 13, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `gender` int(255) NOT NULL DEFAULT 0,
  `telphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `third_party_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telphone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (4, '1', '1', 0, '12312341234', 'byphone', '');
INSERT INTO `user_info` VALUES (5, '田心', '21', 0, '18271897033', 'byphone', '');
INSERT INTO `user_info` VALUES (6, '1', '1', 0, '18271891111', 'byphone', '');
INSERT INTO `user_info` VALUES (7, '1', '150', 0, '12112341234', 'byphone', '');
INSERT INTO `user_info` VALUES (8, '2', '2', 0, '22222222222', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (9, 'xMpCOKC5I4INzFCab3WEmw==', 4);
INSERT INTO `user_password` VALUES (10, '4QrcOUm6Wau+VuBX8g+IPg==', 5);
INSERT INTO `user_password` VALUES (11, 'xMpCOKC5I4INzFCab3WEmw==', 6);
INSERT INTO `user_password` VALUES (12, 'xMpCOKC5I4INzFCab3WEmw==', 7);
INSERT INTO `user_password` VALUES (13, 'yB5yjZ1ML2NvBn+JzBSGLA==', 8);

SET FOREIGN_KEY_CHECKS = 1;
