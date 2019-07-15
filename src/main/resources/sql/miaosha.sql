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

 Date: 15/07/2019 23:26:57
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
INSERT INTO `item` VALUES (27, '6', 6.00, '6', 100, '6');
INSERT INTO `item` VALUES (28, 'iphone', 5000.00, '手机', 99, 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4016674015,960487752&fm=27&gp=0.jpg');
INSERT INTO `item` VALUES (29, '1', 1.00, '1', 88, '1');

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
INSERT INTO `item_stock` VALUES (24, 6, 27);
INSERT INTO `item_stock` VALUES (25, 20, 28);
INSERT INTO `item_stock` VALUES (26, 1, 29);

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
