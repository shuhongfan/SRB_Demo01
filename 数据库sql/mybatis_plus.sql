/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.120.20
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.120.20:3306
 Source Schema         : mybatis_plus

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 30/07/2022 17:20:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` int NULL DEFAULT 0 COMMENT '价格',
  `version` int NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '笔记本', 170, 3);

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `NAME` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` int NULL DEFAULT 0 COMMENT '价格',
  `VERSION` int NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES (1, '外星人笔记本', 120, 5);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uid` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` int NULL DEFAULT NULL,
  `is_deleted` int NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1525429471480401937 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'Jone', 18, 'test1@baomidou.com', NULL, 0);
INSERT INTO `t_user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', NULL, 1);
INSERT INTO `t_user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', NULL, 1);
INSERT INTO `t_user` VALUES (4, '李斯', 21, 'lll@23.com', NULL, 0);
INSERT INTO `t_user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', NULL, 0);
INSERT INTO `t_user` VALUES (1525427978865999873, 'ybc1', 20, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051778, 'ybc1', 22, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051779, 'ybc1', 24, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051780, 'ybc1', 26, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051781, 'ybc1', 28, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051782, 'ybc1', 30, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051783, 'ybc1', 32, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051784, 'ybc1', 34, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051785, 'ybc1', 36, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525427978975051786, 'ybc1', 38, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072088621057, 'ybc0', 20, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033089, 'ybc1', 22, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033090, 'ybc2', 28, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033091, 'ybc3', 38, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033092, 'ybc4', 52, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033093, 'ybc5', 70, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033094, 'ybc6', 92, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033095, 'ybc7', 118, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033096, 'ybc8', 148, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525428072227033097, 'ybc9', 182, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471362961409, 'ybc0', 20, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401922, 'ybc1', 22, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401923, 'ybc2', 28, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401924, 'ybc3', 38, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401925, 'ybc4', 52, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401926, 'ybc5', 70, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401927, 'ybc6', 92, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401928, 'ybc7', 118, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401929, 'ybc8', 148, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401930, 'ybc9', 182, NULL, NULL, 1);
INSERT INTO `t_user` VALUES (1525429471480401931, '张三', 23, 'shbdh@fs.c', NULL, 0);
INSERT INTO `t_user` VALUES (1525429471480401932, '张三', 23, 'shbdh@fs.c', NULL, 0);
INSERT INTO `t_user` VALUES (1525429471480401933, '张三', 23, 'shbdh@fs.c', NULL, 0);
INSERT INTO `t_user` VALUES (1525429471480401934, '张三', 23, 'shbdh@fs.c', NULL, 0);
INSERT INTO `t_user` VALUES (1525429471480401935, '张三', 23, 'shbdh@fs.c', NULL, 0);
INSERT INTO `t_user` VALUES (1525429471480401936, '张杰', 33, NULL, 1, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `deleted` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `version` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Jone', 18, 'test1@baomidou.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, 'Jack', 20, 'test2@baomidou.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (1544866548976758786, '尚硅谷', 9, 'shf@qq.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (1544866754736721921, '尚硅谷', 9, 'shf@qq.com', 1, NULL, NULL, NULL);
INSERT INTO `user` VALUES (1544866935712555010, '尚硅谷上海', 9, 'shf@qq.com', 0, NULL, NULL, NULL);
INSERT INTO `user` VALUES (1547771425893679105, '张三', 20, '123@ss.com', 0, NULL, '2022-07-15 11:02:43', NULL);
INSERT INTO `user` VALUES (1547775179825098754, 'lucy', 20, '123@ss.com', 0, '2022-07-15 10:48:54', '2022-07-15 10:48:54', NULL);
INSERT INTO `user` VALUES (1547778904669044737, '张三', 20, '123@ss.com', 0, '2022-07-15 11:03:42', '2022-07-15 11:03:57', 2);

-- ----------------------------
-- Table structure for user1
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1`  (
  `uid` bigint NOT NULL COMMENT '主键ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user1
-- ----------------------------
INSERT INTO `user1` VALUES (1, 'Jone', 74, 'test1@baomidou.com', NULL, '2022-07-22 10:59:57', 1);
INSERT INTO `user1` VALUES (2, 'Jack', 20, 'test2@baomidou.com', NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (3, 'Tom', 28, 'test3@baomidou.com', NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (5, 'Billie', 24, 'test5@baomidou.com', NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550332253935435777, '建国', 74, '222@er.com', NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550336688606625793, '花花0', 20, NULL, NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550336688682123266, '花花1', 21, NULL, NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550336688682123267, '花花2', 22, NULL, NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550336688682123268, '花花3', 23, NULL, NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550336688682123269, '花花4', 24, NULL, NULL, '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550411058955886594, '建国1', 74, '222@er.com', '2022-07-22 17:22:56', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550411445406425090, '建国1', 74, '222@er.com', '2022-07-22 17:24:28', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550412309907030017, '建国2', 75, '222@er.com', '2022-07-22 09:27:54', '2022-07-23 11:33:56', 0);
INSERT INTO `user1` VALUES (1550415459342770177, '花花0', 20, NULL, '2022-07-22 17:40:25', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550415459476987905, '花花1', 21, NULL, '2022-07-22 17:40:26', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550415459476987906, '花花2', 22, NULL, '2022-07-22 17:40:26', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550415459476987907, '花花3', 23, NULL, '2022-07-22 17:40:26', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550415459476987908, '花花4', 24, NULL, '2022-07-22 17:40:26', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550417418850357250, '建国3', NULL, '222@er.com', '2022-07-22 17:48:13', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550417823793549314, '建国3', NULL, '222@er.com', '2022-07-22 17:49:49', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550418414229966850, '建国3', 33, '222@er.com', '2022-07-22 17:52:10', '2022-07-22 10:59:52', 0);
INSERT INTO `user1` VALUES (1550418496346038274, '建国3', 74, '222@er.com', '2022-07-22 17:52:30', '2022-07-22 10:59:52', 0);

SET FOREIGN_KEY_CHECKS = 1;
