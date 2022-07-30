/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.120.20
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.120.20:3306
 Source Schema         : hfb

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 30/07/2022 17:20:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '用户code',
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '帐户可用余额',
  `freeze_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '投资中冻结金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账号表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (15, 'ed5f02c8c38845f7844ec8600a676bc2', 0.00, 0.00, '2022-07-27 02:44:37', '2022-07-27 02:44:37', 0);
INSERT INTO `user_account` VALUES (16, '00ad93e70341467b83991795d4a45e21', 64999.00, 0.00, '2022-07-27 07:14:14', '2022-07-27 07:14:14', 0);
INSERT INTO `user_account` VALUES (17, '0b1dda6dbf94441ab1d2b420f3ddebb1', 505410.00, 0.00, '2022-07-28 02:41:04', '2022-07-28 02:41:04', 0);
INSERT INTO `user_account` VALUES (18, '187235a6db104bc29812f7f700600733', 1000000.00, 0.00, '2022-07-30 07:28:50', '2022-07-30 07:28:50', 0);
INSERT INTO `user_account` VALUES (19, 'be694b0593884a77aa118653b44f2f99', 1940000.00, 0.00, '2022-07-30 07:42:48', '2022-07-30 07:42:48', 0);
INSERT INTO `user_account` VALUES (20, 'b00643d8f3654f40834c2e15e2dd9dff', 490000.02, 0.00, '2022-07-30 07:45:09', '2022-07-30 07:45:09', 0);

-- ----------------------------
-- Table structure for user_bind
-- ----------------------------
DROP TABLE IF EXISTS `user_bind`;
CREATE TABLE `user_bind`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_id` int NOT NULL DEFAULT 0 COMMENT '商户id',
  `agent_user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT 'P2P商户的用户id',
  `personal_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '身份证号',
  `bank_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `timestamp` bigint NULL DEFAULT NULL,
  `bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定的汇付宝id',
  `pay_passwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付密码',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户绑定表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_bind
-- ----------------------------
INSERT INTO `user_bind` VALUES (16, 999888, '7', '凡凡', '13433333333', '4201022222222362', '5622222222222', NULL, 'http://localhost:3000/user', 'http://localhost/api/core/userBind/notify', 1658906044768, '00ad93e70341467b83991795d4a45e21', '123456', 0, '2022-07-27 07:14:14', '2022-07-27 07:14:14', 0);
INSERT INTO `user_bind` VALUES (17, 999888, '10', '李林', '13535369896', '523623124578965456', '653232315648793', NULL, 'http://localhost:3000/user', 'http://localhost/api/core/userBind/notify', 1658976057992, '0b1dda6dbf94441ab1d2b420f3ddebb1', '123456', 0, '2022-07-28 02:41:04', '2022-07-28 02:41:04', 0);
INSERT INTO `user_bind` VALUES (18, 999888, '8', 'jack', '13456896531', '4855634365986543', '6865979856233', NULL, 'http://localhost:3000/user', 'http://localhost/api/core/userBind/notify', 1659166122864, '187235a6db104bc29812f7f700600733', '123456', 0, '2022-07-30 07:28:50', '2022-07-30 07:28:50', 0);
INSERT INTO `user_bind` VALUES (19, 999888, '12', '张九九', '13439999999', '5656235632924533', '652323652362396', NULL, 'http://localhost:3000/user', 'http://localhost/api/core/userBind/notify', 1659166965162, 'be694b0593884a77aa118653b44f2f99', '123456', 0, '2022-07-30 07:42:48', '2022-07-30 07:42:48', 0);
INSERT INTO `user_bind` VALUES (20, 999888, '13', '李八吧', '13438888888', '987654321453013', '356324563215454568', NULL, 'http://localhost:3000/user', 'http://localhost/api/core/userBind/notify', 1659167107070, 'b00643d8f3654f40834c2e15e2dd9dff', '123456', 0, '2022-07-30 07:45:09', '2022-07-30 07:45:09', 0);

-- ----------------------------
-- Table structure for user_invest
-- ----------------------------
DROP TABLE IF EXISTS `user_invest`;
CREATE TABLE `user_invest`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `vote_bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '投资人绑定协议号',
  `benefit_bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '借款人绑定协议号',
  `agent_project_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '项目编号',
  `agent_project_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '项目名称',
  `agent_bill_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商户订单号',
  `vote_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资金额',
  `vote_prize_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资奖励金额',
  `vote_fee_amt` decimal(10, 2) NULL DEFAULT 0.00 COMMENT 'P2P商户手续费',
  `project_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '项目总金额',
  `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投资备注',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0：默认 1：已放款 -1：已撤标）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户投资表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_invest
-- ----------------------------
INSERT INTO `user_invest` VALUES (7, '00ad93e70341467b83991795d4a45e21', '0b1dda6dbf94441ab1d2b420f3ddebb1', 'LEND20220728165306463', '新中国', 'INVEST20220729154559884', 1000.00, 0.00, 0.00, 100000.00, '', 1, '2022-07-29 07:46:06', '2022-07-29 09:57:52', 0);
INSERT INTO `user_invest` VALUES (8, '00ad93e70341467b83991795d4a45e21', '0b1dda6dbf94441ab1d2b420f3ddebb1', 'LEND20220728165306463', '新中国', 'INVEST20220729161037880', 5000.00, 0.00, 0.00, 100000.00, '', 1, '2022-07-29 08:10:41', '2022-07-29 09:57:52', 0);
INSERT INTO `user_invest` VALUES (9, 'be694b0593884a77aa118653b44f2f99', 'b00643d8f3654f40834c2e15e2dd9dff', 'LEND20220730154841823', '装修贷001', 'INVEST20220730154903301', 20000.00, 0.00, 0.00, 100000.00, '', 1, '2022-07-30 07:49:05', '2022-07-30 07:56:33', 0);
INSERT INTO `user_invest` VALUES (10, 'be694b0593884a77aa118653b44f2f99', 'b00643d8f3654f40834c2e15e2dd9dff', 'LEND20220730154841823', '装修贷001', 'INVEST20220730155031509', 40000.00, 0.00, 0.00, 100000.00, '', 1, '2022-07-30 07:50:35', '2022-07-30 07:56:33', 0);
INSERT INTO `user_invest` VALUES (11, '00ad93e70341467b83991795d4a45e21', 'b00643d8f3654f40834c2e15e2dd9dff', 'LEND20220730154841823', '装修贷001', 'INVEST20220730155452405', 40000.00, 0.00, 0.00, 100000.00, '', 1, '2022-07-30 07:54:54', '2022-07-30 07:56:33', 0);

-- ----------------------------
-- Table structure for user_item_return
-- ----------------------------
DROP TABLE IF EXISTS `user_item_return`;
CREATE TABLE `user_item_return`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_return_id` bigint NOT NULL DEFAULT 0 COMMENT '还款id',
  `agent_project_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '还款项目编号',
  `vote_bill_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '投资单号',
  `to_bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0.00' COMMENT '收款人（投资人）',
  `transit_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '还款金额',
  `base_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '还款本金',
  `benifit_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '还款利息',
  `fee_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT '商户手续费',
  `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户还款明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_item_return
-- ----------------------------

-- ----------------------------
-- Table structure for user_return
-- ----------------------------
DROP TABLE IF EXISTS `user_return`;
CREATE TABLE `user_return`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `agent_goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '商户商品名称',
  `agent_batch_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '批次号',
  `from_bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '还款人绑定协议号',
  `total_amt` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '还款总额',
  `vote_fee_amt` decimal(10, 2) NULL DEFAULT NULL COMMENT 'P2P商户手续费',
  `data` json NULL COMMENT '还款明细数据',
  `note` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户还款表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_return
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
