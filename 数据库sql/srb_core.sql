/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.120.20
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.120.20:3306
 Source Schema         : srb_core

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 30/07/2022 17:20:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for borrow_info
-- ----------------------------
DROP TABLE IF EXISTS `borrow_info`;
CREATE TABLE `borrow_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '借款用户id',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款金额',
  `period` int NULL DEFAULT NULL COMMENT '借款期限',
  `borrow_year_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `money_use` tinyint NULL DEFAULT NULL COMMENT '资金用途',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0：未提交，1：审核中， 2：审核通过， -1：审核不通过）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '借款信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrow_info
-- ----------------------------
INSERT INTO `borrow_info` VALUES (6, 10, 100000.00, 24, 0.12, 4, 2, 2, '2022-07-28 02:41:39', '2022-07-28 02:41:39', 0);
INSERT INTO `borrow_info` VALUES (7, 13, 100000.00, 24, 0.12, 2, 4, 2, '2022-07-30 07:48:03', '2022-07-30 07:48:03', 0);

-- ----------------------------
-- Table structure for borrower
-- ----------------------------
DROP TABLE IF EXISTS `borrower`;
CREATE TABLE `borrower`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '身份证号',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别（1：男 0：女）',
  `age` tinyint NULL DEFAULT NULL COMMENT '年龄',
  `education` tinyint NULL DEFAULT NULL COMMENT '学历',
  `is_marry` tinyint(1) NULL DEFAULT NULL COMMENT '是否结婚（1：是 0：否）',
  `industry` tinyint NULL DEFAULT NULL COMMENT '行业',
  `income` tinyint NULL DEFAULT NULL COMMENT '月收入',
  `return_source` tinyint NULL DEFAULT NULL COMMENT '还款来源',
  `contacts_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人名称',
  `contacts_mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `contacts_relation` tinyint NULL DEFAULT NULL COMMENT '联系人关系',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0：未认证，1：认证中， 2：认证通过， -1：认证失败）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '借款人' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrower
-- ----------------------------
INSERT INTO `borrower` VALUES (4, 8, '13437587875', '440825197510011629', '13437587875', 1, 18, 4, 1, 1, 4, 1, '斌斌', '13435365325', 2, 2, '2022-07-27 08:51:42', '2022-07-27 10:44:55', 0);
INSERT INTO `borrower` VALUES (5, 10, '13447659635', '140227197206163424', '13447659635', 0, 40, 3, 0, 1, 3, 2, '李光明', '13456569863', 3, 2, '2022-07-27 10:47:51', '2022-07-27 10:47:51', 0);
INSERT INTO `borrower` VALUES (6, 13, '李八吧', '987654321453013', '13438888888', 1, 45, 1, 1, 3, 2, 2, '李八', '13438888899', 2, 2, '2022-07-30 07:47:16', '2022-07-30 07:47:16', 0);

-- ----------------------------
-- Table structure for borrower_attach
-- ----------------------------
DROP TABLE IF EXISTS `borrower_attach`;
CREATE TABLE `borrower_attach`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `borrower_id` bigint NOT NULL DEFAULT 0 COMMENT '借款人id',
  `image_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片类型（idCard1：身份证正面，idCard2：身份证反面，house：房产证，car：车）',
  `image_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `image_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_borrower_id`(`borrower_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '借款人上传资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of borrower_attach
-- ----------------------------
INSERT INTO `borrower_attach` VALUES (13, 4, 'idCard1', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard1/2022/07/27/eb84a2f6-298e-4dc1-a009-d52feeeef5ba.jpg', '正面1.jpg', '2022-07-27 08:51:43', '2022-07-27 08:51:43', 0);
INSERT INTO `borrower_attach` VALUES (14, 4, 'idCard2', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard2/2022/07/27/88f7f80e-57fb-4d7d-82a2-b0214f95cdce.jpg', '反面.jpg', '2022-07-27 08:51:43', '2022-07-27 08:51:43', 0);
INSERT INTO `borrower_attach` VALUES (15, 4, 'house', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/house/2022/07/27/3c8b048f-8c17-43bb-b9ae-4d3aa8a92218.jpg', 'house.jpg', '2022-07-27 08:51:43', '2022-07-27 08:51:43', 0);
INSERT INTO `borrower_attach` VALUES (16, 4, 'car', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/car/2022/07/27/a47fbf45-6ad8-442a-a6a1-189f386ca50e.jpg', 'car.jpg', '2022-07-27 08:51:43', '2022-07-27 08:51:43', 0);
INSERT INTO `borrower_attach` VALUES (17, 5, 'idCard1', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard1/2022/07/27/006a7051-5f04-4fab-8ca5-0070ae283048.jpg', '正面2.jpg', '2022-07-27 10:47:51', '2022-07-27 10:47:51', 0);
INSERT INTO `borrower_attach` VALUES (18, 5, 'idCard2', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard2/2022/07/27/1138846c-94f9-4bb8-9e68-85c08c2353c4.jpg', '反面.jpg', '2022-07-27 10:47:51', '2022-07-27 10:47:51', 0);
INSERT INTO `borrower_attach` VALUES (19, 5, 'house', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/house/2022/07/27/d1f1660e-7d65-43f6-bdbb-5698858846fb.jpg', 'house.jpg', '2022-07-27 10:47:51', '2022-07-27 10:47:51', 0);
INSERT INTO `borrower_attach` VALUES (20, 5, 'car', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/car/2022/07/27/f656581a-22e7-4791-9224-bf8f5c02c2d4.jpg', 'car.jpg', '2022-07-27 10:47:51', '2022-07-27 10:47:51', 0);
INSERT INTO `borrower_attach` VALUES (21, 6, 'idCard1', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard1/2022/07/30/416692b1-bd8f-4b84-a8db-21a217222a3e.jpg', '正面2.jpg', '2022-07-30 07:47:16', '2022-07-30 07:47:16', 0);
INSERT INTO `borrower_attach` VALUES (22, 6, 'idCard2', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/idCard2/2022/07/30/1b764a31-3ed6-4514-b609-d7469f3629bb.jpg', '反面.jpg', '2022-07-30 07:47:16', '2022-07-30 07:47:16', 0);
INSERT INTO `borrower_attach` VALUES (23, 6, 'house', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/house/2022/07/30/0e8a22d9-fa6e-4e1c-a5b5-aa018a529bb4.jpg', 'house.jpg', '2022-07-30 07:47:16', '2022-07-30 07:47:16', 0);
INSERT INTO `borrower_attach` VALUES (24, 6, 'car', 'https://srb-shf.oss-cn-hangzhou.aliyuncs.com/car/2022/07/30/5e93baf2-fad7-4bab-bc54-801a0e823c9c.jpg', 'car.jpg', '2022-07-30 07:47:16', '2022-07-30 07:47:16', 0);

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '上级id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `value` int NULL DEFAULT NULL COMMENT '值',
  `dict_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_parent_id_value`(`parent_id` ASC, `value` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82008 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, 0, '全部分类', NULL, 'ROOT', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20000, 1, '行业', NULL, 'industry', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20001, 20000, 'IT', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20002, 20000, '医生', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20003, 20000, '教师', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20004, 20000, '导游', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20005, 20000, '律师', 5, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (20006, 20000, '其他', 6, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30000, 1, '学历', NULL, 'education', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30001, 30000, '高中', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30002, 30000, '大专', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30003, 30000, '本科', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30004, 30000, '研究生', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (30005, 30000, '其他', 5, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (40000, 1, '收入', NULL, 'income', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (40001, 40000, '0-3000', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (40002, 40000, '3000-5000', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (40003, 40000, '5000-10000', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (40004, 40000, '10000以上', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (50000, 1, '收入来源', NULL, 'returnSource', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (50001, 50000, '工资', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (50002, 50000, '股票', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (50003, 50000, '兼职', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (60000, 1, '关系', NULL, 'relation', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (60001, 60000, '夫妻', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (60002, 60000, '兄妹', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (60003, 60000, '父母', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (60004, 60000, '其他', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (70000, 1, '还款方式', NULL, 'returnMethod', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (70001, 70000, '等额本息', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (70002, 70000, '等额本金', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (70003, 70000, '每月还息一次还本', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (70004, 70000, '一次还本还息', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80000, 1, '资金用途', NULL, 'moneyUse', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80001, 80000, '旅游', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80002, 80000, '买房', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80003, 80000, '装修', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80004, 80000, '医疗', 4, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80005, 80000, '美容', 5, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (80006, 80000, '其他', 6, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81000, 1, '借款状态', NULL, 'borrowStatus', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81001, 81000, '待审核', 0, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81002, 81000, '审批通过', 1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81003, 81000, '还款中', 2, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81004, 81000, '结束', 3, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (81005, 81000, '审批不通过', -1, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82000, 1, '学校性质', NULL, 'SchoolStatus', '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82001, 82000, '211/985', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82002, 82000, '一本', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82003, 82000, '二本', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82004, 82000, '三本', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82005, 82000, '高职高专', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82006, 82000, '中职中专', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);
INSERT INTO `dict` VALUES (82007, 82000, '高中及以下', NULL, NULL, '2022-07-25 03:04:11', '2022-07-25 03:04:11', 0);

-- ----------------------------
-- Table structure for integral_grade
-- ----------------------------
DROP TABLE IF EXISTS `integral_grade`;
CREATE TABLE `integral_grade`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `integral_start` int NULL DEFAULT NULL COMMENT '积分区间开始',
  `integral_end` int NULL DEFAULT NULL COMMENT '积分区间结束',
  `borrow_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款额度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分等级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of integral_grade
-- ----------------------------
INSERT INTO `integral_grade` VALUES (1, 10, 50, 10000.00, '2020-12-08 17:02:29', '2022-07-24 02:16:16', 0);
INSERT INTO `integral_grade` VALUES (2, 51, 100, 30000.00, '2020-12-08 17:02:42', '2021-02-19 18:00:25', 0);
INSERT INTO `integral_grade` VALUES (3, 101, 1000, 100000.00, '2020-12-08 17:02:57', '2022-07-24 03:22:16', 0);
INSERT INTO `integral_grade` VALUES (4, 1001, 10000, 1000000.00, '2022-07-24 03:21:36', '2022-07-24 10:08:29', 1);

-- ----------------------------
-- Table structure for lend
-- ----------------------------
DROP TABLE IF EXISTS `lend`;
CREATE TABLE `lend`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '借款用户id',
  `borrow_info_id` bigint NULL DEFAULT NULL COMMENT '借款信息id',
  `lend_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标的编号',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '标的金额',
  `period` int NULL DEFAULT NULL COMMENT '投资期数',
  `lend_year_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `service_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '平台服务费率',
  `return_method` tinyint NULL DEFAULT NULL COMMENT '还款方式',
  `lowest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '最低投资金额',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '已投金额',
  `invest_num` int NULL DEFAULT NULL COMMENT '投资人数',
  `publish_date` datetime NULL DEFAULT NULL COMMENT '发布日期',
  `lend_start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `lend_end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `lend_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说明',
  `expect_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '平台预期收益',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际收益',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态',
  `check_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `check_admin_id` bigint NULL DEFAULT NULL COMMENT '审核用户id',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '放款时间',
  `payment_admin_id` datetime NULL DEFAULT NULL COMMENT '放款人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_lend_no`(`lend_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_borrow_info_id`(`borrow_info_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标的准备表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lend
-- ----------------------------
INSERT INTO `lend` VALUES (4, 10, 6, 'LEND20220728165306463', '新中国', 100000.00, 24, 0.12, 0.05, 4, 100.00, 6000.00, 2, '2022-07-28 16:53:07', '2022-07-29', '2024-07-29', NULL, 9999.98, 600.00, 2, '2022-07-28 16:53:07', 1, '2022-07-29 17:59:06', NULL, '2022-07-28 08:53:05', '2022-07-28 08:53:05', 0);
INSERT INTO `lend` VALUES (5, 13, 7, 'LEND20220730154841823', '装修贷001', 100000.00, 24, 0.12, 0.05, 2, 100.00, 100000.00, 3, '2022-07-30 15:48:42', '2022-07-30', '2024-07-30', NULL, 9999.98, 9999.98, 2, '2022-07-30 15:48:42', 1, '2022-07-30 15:57:35', NULL, '2022-07-30 07:48:40', '2022-07-30 07:48:40', 0);

-- ----------------------------
-- Table structure for lend_item
-- ----------------------------
DROP TABLE IF EXISTS `lend_item`;
CREATE TABLE `lend_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_item_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投资编号',
  `lend_id` bigint NOT NULL DEFAULT 0 COMMENT '标的id',
  `invest_user_id` bigint NULL DEFAULT NULL COMMENT '投资用户id',
  `invest_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投资人名称',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '投资金额',
  `lend_year_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `invest_time` datetime NULL DEFAULT NULL COMMENT '投资时间',
  `lend_start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `lend_end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `expect_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '预期收益',
  `real_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际收益',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态（0：默认 1：已支付 2：已还款）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_lend_item_no`(`lend_item_no` ASC) USING BTREE,
  INDEX `idx_lend_id`(`lend_id` ASC) USING BTREE,
  INDEX `idx_invest_user_id`(`invest_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标的出借记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lend_item
-- ----------------------------
INSERT INTO `lend_item` VALUES (7, 'INVEST20220729154559884', 4, 7, '凡凡', 1000.00, 0.12, '2022-07-29 15:46:00', '2022-07-29', '2024-07-29', 240.00, 0.00, 1, '2022-07-29 07:45:59', '2022-07-29 07:45:59', 0);
INSERT INTO `lend_item` VALUES (8, 'INVEST20220729161037880', 4, 7, '凡凡', 5000.00, 0.12, '2022-07-29 16:10:38', '2022-07-29', '2024-07-29', 1200.00, 0.00, 1, '2022-07-29 08:10:37', '2022-07-29 08:10:37', 0);
INSERT INTO `lend_item` VALUES (9, 'INVEST20220730154903301', 5, 12, '13439999999', 20000.00, 0.12, '2022-07-30 15:49:04', '2022-07-30', '2024-07-30', 2499.68, 0.00, 1, '2022-07-30 07:49:02', '2022-07-30 07:49:02', 0);
INSERT INTO `lend_item` VALUES (10, 'INVEST20220730155031509', 5, 12, '13439999999', 40000.00, 0.12, '2022-07-30 15:50:32', '2022-07-30', '2024-07-30', 4999.76, 0.00, 1, '2022-07-30 07:50:30', '2022-07-30 07:50:30', 0);
INSERT INTO `lend_item` VALUES (11, 'INVEST20220730155452405', 5, 7, '凡凡', 40000.00, 0.12, '2022-07-30 15:54:52', '2022-07-30', '2024-07-30', 4999.76, 0.00, 1, '2022-07-30 07:54:50', '2022-07-30 07:54:50', 0);

-- ----------------------------
-- Table structure for lend_item_return
-- ----------------------------
DROP TABLE IF EXISTS `lend_item_return`;
CREATE TABLE `lend_item_return`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_return_id` bigint NULL DEFAULT NULL COMMENT '标的还款id',
  `lend_item_id` bigint NULL DEFAULT NULL COMMENT '标的项id',
  `lend_id` bigint NOT NULL DEFAULT 0 COMMENT '标的id',
  `invest_user_id` bigint NULL DEFAULT NULL COMMENT '出借用户id',
  `invest_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '出借金额',
  `current_period` int NULL DEFAULT NULL COMMENT '当前的期数',
  `lend_year_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10, 2) NULL DEFAULT NULL COMMENT '本金',
  `interest` decimal(10, 2) NULL DEFAULT NULL COMMENT '利息',
  `total` decimal(10, 2) NULL DEFAULT NULL COMMENT '本息',
  `fee` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '手续费',
  `return_date` date NULL DEFAULT NULL COMMENT '还款时指定的还款日期',
  `real_return_time` datetime NULL DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) NULL DEFAULT NULL COMMENT '是否逾期',
  `overdue_total` decimal(10, 2) NULL DEFAULT NULL COMMENT '逾期金额',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_lend_return_id`(`lend_return_id` ASC) USING BTREE,
  INDEX `idx_lend_item_id`(`lend_item_id` ASC) USING BTREE,
  INDEX `idx_lend_id`(`lend_id` ASC) USING BTREE,
  INDEX `idx_invest_user_id`(`invest_user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标的出借回款记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lend_item_return
-- ----------------------------
INSERT INTO `lend_item_return` VALUES (16, 34, 7, 4, 7, 1000.00, 1, 0.12, 4, 1000.00, 240.00, 1240.00, 0.00, '2022-08-29', NULL, 0, NULL, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_item_return` VALUES (17, 34, 8, 4, 7, 5000.00, 1, 0.12, 4, 5000.00, 1200.00, 6200.00, 0.00, '2022-08-29', NULL, 0, NULL, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_item_return` VALUES (18, 106, 9, 5, 12, 20000.00, 1, 0.12, 2, 833.33, 199.99, 1033.32, 0.00, '2022-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (19, 107, 9, 5, 12, 20000.00, 2, 0.12, 2, 833.33, 191.65, 1024.98, 0.00, '2022-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (20, 108, 9, 5, 12, 20000.00, 3, 0.12, 2, 833.33, 183.32, 1016.65, 0.00, '2022-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (21, 109, 9, 5, 12, 20000.00, 4, 0.12, 2, 833.33, 174.99, 1008.32, 0.00, '2022-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (22, 110, 9, 5, 12, 20000.00, 5, 0.12, 2, 833.33, 166.65, 999.98, 0.00, '2022-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (23, 111, 9, 5, 12, 20000.00, 6, 0.12, 2, 833.33, 158.32, 991.65, 0.00, '2023-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (24, 112, 9, 5, 12, 20000.00, 7, 0.12, 2, 833.33, 149.99, 983.32, 0.00, '2023-02-28', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (25, 113, 9, 5, 12, 20000.00, 8, 0.12, 2, 833.33, 141.65, 974.98, 0.00, '2023-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (26, 114, 9, 5, 12, 20000.00, 9, 0.12, 2, 833.33, 133.32, 966.65, 0.00, '2023-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (27, 115, 9, 5, 12, 20000.00, 10, 0.12, 2, 833.33, 124.99, 958.32, 0.00, '2023-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (28, 116, 9, 5, 12, 20000.00, 11, 0.12, 2, 833.33, 116.65, 949.98, 0.00, '2023-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (29, 117, 9, 5, 12, 20000.00, 12, 0.12, 2, 833.33, 108.32, 941.65, 0.00, '2023-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (30, 118, 9, 5, 12, 20000.00, 13, 0.12, 2, 833.33, 99.99, 933.32, 0.00, '2023-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (31, 119, 9, 5, 12, 20000.00, 14, 0.12, 2, 833.33, 91.65, 924.98, 0.00, '2023-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (32, 120, 9, 5, 12, 20000.00, 15, 0.12, 2, 833.33, 83.32, 916.65, 0.00, '2023-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (33, 121, 9, 5, 12, 20000.00, 16, 0.12, 2, 833.33, 74.99, 908.32, 0.00, '2023-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (34, 122, 9, 5, 12, 20000.00, 17, 0.12, 2, 833.33, 66.65, 899.98, 0.00, '2023-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (35, 123, 9, 5, 12, 20000.00, 18, 0.12, 2, 833.33, 58.32, 891.65, 0.00, '2024-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (36, 124, 9, 5, 12, 20000.00, 19, 0.12, 2, 833.33, 49.99, 883.32, 0.00, '2024-02-29', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (37, 125, 9, 5, 12, 20000.00, 20, 0.12, 2, 833.33, 41.65, 874.98, 0.00, '2024-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (38, 126, 9, 5, 12, 20000.00, 21, 0.12, 2, 833.33, 33.32, 866.65, 0.00, '2024-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (39, 127, 9, 5, 12, 20000.00, 22, 0.12, 2, 833.33, 24.99, 858.32, 0.00, '2024-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (40, 128, 9, 5, 12, 20000.00, 23, 0.12, 2, 833.33, 16.65, 849.98, 0.00, '2024-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (41, 129, 9, 5, 12, 20000.00, 24, 0.12, 2, 833.33, 8.32, 841.65, 0.00, '2024-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (42, 106, 10, 5, 12, 40000.00, 1, 0.12, 2, 1666.67, 399.99, 2066.66, 0.00, '2022-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (43, 107, 10, 5, 12, 40000.00, 2, 0.12, 2, 1666.67, 383.32, 2049.99, 0.00, '2022-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (44, 108, 10, 5, 12, 40000.00, 3, 0.12, 2, 1666.67, 366.66, 2033.33, 0.00, '2022-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (45, 109, 10, 5, 12, 40000.00, 4, 0.12, 2, 1666.67, 349.99, 2016.66, 0.00, '2022-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (46, 110, 10, 5, 12, 40000.00, 5, 0.12, 2, 1666.67, 333.32, 1999.99, 0.00, '2022-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (47, 111, 10, 5, 12, 40000.00, 6, 0.12, 2, 1666.67, 316.66, 1983.33, 0.00, '2023-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (48, 112, 10, 5, 12, 40000.00, 7, 0.12, 2, 1666.67, 299.99, 1966.66, 0.00, '2023-02-28', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (49, 113, 10, 5, 12, 40000.00, 8, 0.12, 2, 1666.67, 283.32, 1949.99, 0.00, '2023-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (50, 114, 10, 5, 12, 40000.00, 9, 0.12, 2, 1666.67, 266.66, 1933.33, 0.00, '2023-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (51, 115, 10, 5, 12, 40000.00, 10, 0.12, 2, 1666.67, 249.99, 1916.66, 0.00, '2023-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (52, 116, 10, 5, 12, 40000.00, 11, 0.12, 2, 1666.67, 233.32, 1899.99, 0.00, '2023-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (53, 117, 10, 5, 12, 40000.00, 12, 0.12, 2, 1666.67, 216.66, 1883.33, 0.00, '2023-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (54, 118, 10, 5, 12, 40000.00, 13, 0.12, 2, 1666.67, 199.99, 1866.66, 0.00, '2023-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (55, 119, 10, 5, 12, 40000.00, 14, 0.12, 2, 1666.67, 183.32, 1849.99, 0.00, '2023-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (56, 120, 10, 5, 12, 40000.00, 15, 0.12, 2, 1666.67, 166.66, 1833.33, 0.00, '2023-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (57, 121, 10, 5, 12, 40000.00, 16, 0.12, 2, 1666.67, 149.99, 1816.66, 0.00, '2023-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (58, 122, 10, 5, 12, 40000.00, 17, 0.12, 2, 1666.67, 133.32, 1799.99, 0.00, '2023-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (59, 123, 10, 5, 12, 40000.00, 18, 0.12, 2, 1666.67, 116.66, 1783.33, 0.00, '2024-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (60, 124, 10, 5, 12, 40000.00, 19, 0.12, 2, 1666.67, 99.99, 1766.66, 0.00, '2024-02-29', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (61, 125, 10, 5, 12, 40000.00, 20, 0.12, 2, 1666.67, 83.32, 1749.99, 0.00, '2024-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (62, 126, 10, 5, 12, 40000.00, 21, 0.12, 2, 1666.67, 66.66, 1733.33, 0.00, '2024-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (63, 127, 10, 5, 12, 40000.00, 22, 0.12, 2, 1666.67, 49.99, 1716.66, 0.00, '2024-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (64, 128, 10, 5, 12, 40000.00, 23, 0.12, 2, 1666.67, 33.32, 1699.99, 0.00, '2024-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (65, 129, 10, 5, 12, 40000.00, 24, 0.12, 2, 1666.67, 16.66, 1683.33, 0.00, '2024-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (66, 106, 11, 5, 7, 40000.00, 1, 0.12, 2, 1666.67, 399.99, 2066.66, 0.00, '2022-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (67, 107, 11, 5, 7, 40000.00, 2, 0.12, 2, 1666.67, 383.32, 2049.99, 0.00, '2022-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (68, 108, 11, 5, 7, 40000.00, 3, 0.12, 2, 1666.67, 366.66, 2033.33, 0.00, '2022-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (69, 109, 11, 5, 7, 40000.00, 4, 0.12, 2, 1666.67, 349.99, 2016.66, 0.00, '2022-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (70, 110, 11, 5, 7, 40000.00, 5, 0.12, 2, 1666.67, 333.32, 1999.99, 0.00, '2022-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (71, 111, 11, 5, 7, 40000.00, 6, 0.12, 2, 1666.67, 316.66, 1983.33, 0.00, '2023-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (72, 112, 11, 5, 7, 40000.00, 7, 0.12, 2, 1666.67, 299.99, 1966.66, 0.00, '2023-02-28', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (73, 113, 11, 5, 7, 40000.00, 8, 0.12, 2, 1666.67, 283.32, 1949.99, 0.00, '2023-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (74, 114, 11, 5, 7, 40000.00, 9, 0.12, 2, 1666.67, 266.66, 1933.33, 0.00, '2023-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (75, 115, 11, 5, 7, 40000.00, 10, 0.12, 2, 1666.67, 249.99, 1916.66, 0.00, '2023-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (76, 116, 11, 5, 7, 40000.00, 11, 0.12, 2, 1666.67, 233.32, 1899.99, 0.00, '2023-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (77, 117, 11, 5, 7, 40000.00, 12, 0.12, 2, 1666.67, 216.66, 1883.33, 0.00, '2023-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (78, 118, 11, 5, 7, 40000.00, 13, 0.12, 2, 1666.67, 199.99, 1866.66, 0.00, '2023-08-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (79, 119, 11, 5, 7, 40000.00, 14, 0.12, 2, 1666.67, 183.32, 1849.99, 0.00, '2023-09-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (80, 120, 11, 5, 7, 40000.00, 15, 0.12, 2, 1666.67, 166.66, 1833.33, 0.00, '2023-10-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (81, 121, 11, 5, 7, 40000.00, 16, 0.12, 2, 1666.67, 149.99, 1816.66, 0.00, '2023-11-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (82, 122, 11, 5, 7, 40000.00, 17, 0.12, 2, 1666.67, 133.32, 1799.99, 0.00, '2023-12-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (83, 123, 11, 5, 7, 40000.00, 18, 0.12, 2, 1666.67, 116.66, 1783.33, 0.00, '2024-01-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (84, 124, 11, 5, 7, 40000.00, 19, 0.12, 2, 1666.67, 99.99, 1766.66, 0.00, '2024-02-29', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (85, 125, 11, 5, 7, 40000.00, 20, 0.12, 2, 1666.67, 83.32, 1749.99, 0.00, '2024-03-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (86, 126, 11, 5, 7, 40000.00, 21, 0.12, 2, 1666.67, 66.66, 1733.33, 0.00, '2024-04-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (87, 127, 11, 5, 7, 40000.00, 22, 0.12, 2, 1666.67, 49.99, 1716.66, 0.00, '2024-05-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (88, 128, 11, 5, 7, 40000.00, 23, 0.12, 2, 1666.67, 33.32, 1699.99, 0.00, '2024-06-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_item_return` VALUES (89, 129, 11, 5, 7, 40000.00, 24, 0.12, 2, 1666.67, 16.66, 1683.33, 0.00, '2024-07-30', NULL, 0, NULL, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);

-- ----------------------------
-- Table structure for lend_return
-- ----------------------------
DROP TABLE IF EXISTS `lend_return`;
CREATE TABLE `lend_return`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `lend_id` bigint NULL DEFAULT NULL COMMENT '标的id',
  `borrow_info_id` bigint NOT NULL DEFAULT 0 COMMENT '借款信息id',
  `return_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '还款批次号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '借款人用户id',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '借款金额',
  `base_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '计息本金额',
  `current_period` int NULL DEFAULT NULL COMMENT '当前的期数',
  `lend_year_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT '年化利率',
  `return_method` tinyint NULL DEFAULT NULL COMMENT '还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本',
  `principal` decimal(10, 2) NULL DEFAULT NULL COMMENT '本金',
  `interest` decimal(10, 2) NULL DEFAULT NULL COMMENT '利息',
  `total` decimal(10, 2) NULL DEFAULT NULL COMMENT '本息',
  `fee` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '手续费',
  `return_date` date NULL DEFAULT NULL COMMENT '还款时指定的还款日期',
  `real_return_time` datetime NULL DEFAULT NULL COMMENT '实际发生的还款时间',
  `is_overdue` tinyint(1) NULL DEFAULT NULL COMMENT '是否逾期',
  `overdue_total` decimal(10, 2) NULL DEFAULT NULL COMMENT '逾期金额',
  `is_last` tinyint(1) NULL DEFAULT NULL COMMENT '是否最后一次还款',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态（0-未归还 1-已归还）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_return_no`(`return_no` ASC) USING BTREE,
  INDEX `idx_lend_id`(`lend_id` ASC) USING BTREE,
  INDEX `idx_borrow_info_id`(`borrow_info_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '还款记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lend_return
-- ----------------------------
INSERT INTO `lend_return` VALUES (34, 4, 6, 'RETURN20220729175905134', 10, 100000.00, 6000.00, 1, 0.12, 4, 6000.00, 1440.00, 7440.00, 0.00, '2022-08-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (35, 4, 6, 'RETURN20220729175905908', 10, 100000.00, 6000.00, 2, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2022-09-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (36, 4, 6, 'RETURN20220729175905105', 10, 100000.00, 6000.00, 3, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2022-10-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (37, 4, 6, 'RETURN20220729175905705', 10, 100000.00, 6000.00, 4, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2022-11-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (38, 4, 6, 'RETURN20220729175905274', 10, 100000.00, 6000.00, 5, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2022-12-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (39, 4, 6, 'RETURN20220729175905154', 10, 100000.00, 6000.00, 6, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-01-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (40, 4, 6, 'RETURN20220729175905574', 10, 100000.00, 6000.00, 7, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-02-28', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (41, 4, 6, 'RETURN20220729175905737', 10, 100000.00, 6000.00, 8, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-03-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (42, 4, 6, 'RETURN20220729175905275', 10, 100000.00, 6000.00, 9, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-04-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (43, 4, 6, 'RETURN20220729175905758', 10, 100000.00, 6000.00, 10, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-05-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (44, 4, 6, 'RETURN20220729175905122', 10, 100000.00, 6000.00, 11, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-06-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (45, 4, 6, 'RETURN20220729175905900', 10, 100000.00, 6000.00, 12, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-07-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (46, 4, 6, 'RETURN20220729175905444', 10, 100000.00, 6000.00, 13, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-08-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (47, 4, 6, 'RETURN20220729175905141', 10, 100000.00, 6000.00, 14, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-09-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (48, 4, 6, 'RETURN20220729175905685', 10, 100000.00, 6000.00, 15, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-10-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (49, 4, 6, 'RETURN20220729175905855', 10, 100000.00, 6000.00, 16, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-11-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (50, 4, 6, 'RETURN20220729175905453', 10, 100000.00, 6000.00, 17, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2023-12-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (51, 4, 6, 'RETURN20220729175905286', 10, 100000.00, 6000.00, 18, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-01-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (52, 4, 6, 'RETURN20220729175905561', 10, 100000.00, 6000.00, 19, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-02-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (53, 4, 6, 'RETURN20220729175905581', 10, 100000.00, 6000.00, 20, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-03-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (54, 4, 6, 'RETURN20220729175905759', 10, 100000.00, 6000.00, 21, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-04-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (55, 4, 6, 'RETURN20220729175905347', 10, 100000.00, 6000.00, 22, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-05-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (56, 4, 6, 'RETURN20220729175905695', 10, 100000.00, 6000.00, 23, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-06-29', NULL, 0, NULL, 0, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (57, 4, 6, 'RETURN20220729175905985', 10, 100000.00, 6000.00, 24, 0.12, 4, 0.00, 0.00, 0.00, 0.00, '2024-07-29', NULL, 0, NULL, 1, 0, '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `lend_return` VALUES (106, 5, 7, 'RETURN20220730155734116', 13, 100000.00, 100000.00, 1, 0.12, 2, 4166.67, 999.97, 5166.64, 0.00, '2022-08-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (107, 5, 7, 'RETURN20220730155734683', 13, 100000.00, 100000.00, 2, 0.12, 2, 4166.67, 958.29, 5124.96, 0.00, '2022-09-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (108, 5, 7, 'RETURN20220730155734743', 13, 100000.00, 100000.00, 3, 0.12, 2, 4166.67, 916.64, 5083.31, 0.00, '2022-10-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (109, 5, 7, 'RETURN20220730155734723', 13, 100000.00, 100000.00, 4, 0.12, 2, 4166.67, 874.97, 5041.64, 0.00, '2022-11-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (110, 5, 7, 'RETURN20220730155734465', 13, 100000.00, 100000.00, 5, 0.12, 2, 4166.67, 833.29, 4999.96, 0.00, '2022-12-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (111, 5, 7, 'RETURN20220730155734854', 13, 100000.00, 100000.00, 6, 0.12, 2, 4166.67, 791.64, 4958.31, 0.00, '2023-01-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (112, 5, 7, 'RETURN20220730155734454', 13, 100000.00, 100000.00, 7, 0.12, 2, 4166.67, 749.97, 4916.64, 0.00, '2023-02-28', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (113, 5, 7, 'RETURN20220730155734360', 13, 100000.00, 100000.00, 8, 0.12, 2, 4166.67, 708.29, 4874.96, 0.00, '2023-03-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (114, 5, 7, 'RETURN20220730155734109', 13, 100000.00, 100000.00, 9, 0.12, 2, 4166.67, 666.64, 4833.31, 0.00, '2023-04-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (115, 5, 7, 'RETURN20220730155734350', 13, 100000.00, 100000.00, 10, 0.12, 2, 4166.67, 624.97, 4791.64, 0.00, '2023-05-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (116, 5, 7, 'RETURN20220730155734091', 13, 100000.00, 100000.00, 11, 0.12, 2, 4166.67, 583.29, 4749.96, 0.00, '2023-06-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (117, 5, 7, 'RETURN20220730155734612', 13, 100000.00, 100000.00, 12, 0.12, 2, 4166.67, 541.64, 4708.31, 0.00, '2023-07-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (118, 5, 7, 'RETURN20220730155734321', 13, 100000.00, 100000.00, 13, 0.12, 2, 4166.67, 499.97, 4666.64, 0.00, '2023-08-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (119, 5, 7, 'RETURN20220730155734413', 13, 100000.00, 100000.00, 14, 0.12, 2, 4166.67, 458.29, 4624.96, 0.00, '2023-09-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (120, 5, 7, 'RETURN20220730155734060', 13, 100000.00, 100000.00, 15, 0.12, 2, 4166.67, 416.64, 4583.31, 0.00, '2023-10-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (121, 5, 7, 'RETURN20220730155734579', 13, 100000.00, 100000.00, 16, 0.12, 2, 4166.67, 374.97, 4541.64, 0.00, '2023-11-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (122, 5, 7, 'RETURN20220730155734728', 13, 100000.00, 100000.00, 17, 0.12, 2, 4166.67, 333.29, 4499.96, 0.00, '2023-12-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (123, 5, 7, 'RETURN20220730155734386', 13, 100000.00, 100000.00, 18, 0.12, 2, 4166.67, 291.64, 4458.31, 0.00, '2024-01-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (124, 5, 7, 'RETURN20220730155734509', 13, 100000.00, 100000.00, 19, 0.12, 2, 4166.67, 249.97, 4416.64, 0.00, '2024-02-29', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (125, 5, 7, 'RETURN20220730155734712', 13, 100000.00, 100000.00, 20, 0.12, 2, 4166.67, 208.29, 4374.96, 0.00, '2024-03-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (126, 5, 7, 'RETURN20220730155734685', 13, 100000.00, 100000.00, 21, 0.12, 2, 4166.67, 166.64, 4333.31, 0.00, '2024-04-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (127, 5, 7, 'RETURN20220730155734124', 13, 100000.00, 100000.00, 22, 0.12, 2, 4166.67, 124.97, 4291.64, 0.00, '2024-05-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (128, 5, 7, 'RETURN20220730155734488', 13, 100000.00, 100000.00, 23, 0.12, 2, 4166.67, 83.29, 4249.96, 0.00, '2024-06-30', NULL, 0, NULL, 0, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `lend_return` VALUES (129, 5, 7, 'RETURN20220730155734081', 13, 100000.00, 100000.00, 24, 0.12, 2, 4166.67, 41.64, 4208.31, 0.00, '2024-07-30', NULL, 0, NULL, 1, 0, '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);

-- ----------------------------
-- Table structure for trans_flow
-- ----------------------------
DROP TABLE IF EXISTS `trans_flow`;
CREATE TABLE `trans_flow`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `trans_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '交易单号',
  `trans_type` tinyint NOT NULL DEFAULT 0 COMMENT '交易类型（1：充值 2：提现 3：投标 4：投资回款 ...）',
  `trans_type_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易类型名称',
  `trans_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '交易金额',
  `memo` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_trans_no`(`trans_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '交易流水表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of trans_flow
-- ----------------------------
INSERT INTO `trans_flow` VALUES (57, 10, '李林', '20220729141255338', 1, '充值', 10.00, '充值', '2022-07-29 06:13:01', '2022-07-29 06:13:01', 0);
INSERT INTO `trans_flow` VALUES (58, 7, '凡凡', '20220729145507335', 1, '充值', 10000.00, '充值', '2022-07-29 06:55:11', '2022-07-29 06:55:11', 0);
INSERT INTO `trans_flow` VALUES (59, 7, '凡凡', 'INVEST20220729154559884', 2, '投标锁定', 1000.00, '投资项目编号：LEND20220728165306463,项目名称：新中国', '2022-07-29 07:46:07', '2022-07-29 07:46:07', 0);
INSERT INTO `trans_flow` VALUES (60, 7, '凡凡', 'INVEST20220729161037880', 2, '投标锁定', 5000.00, '投资项目编号：LEND20220728165306463,项目名称：新中国', '2022-07-29 08:10:42', '2022-07-29 08:10:42', 0);
INSERT INTO `trans_flow` VALUES (64, 10, '李林', 'LOAN20220729175905522', 5, '放款到账', 6000.00, '借款放款到账，编号：LEND20220728165306463', '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `trans_flow` VALUES (65, 7, '凡凡', 'TRANS20220729175905749', 3, '放款解锁', 1000.00, '冻结资金转出，出借放款，编号：LEND20220728165306463', '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `trans_flow` VALUES (66, 7, '凡凡', 'TRANS20220729175905435', 3, '放款解锁', 5000.00, '冻结资金转出，出借放款，编号：LEND20220728165306463', '2022-07-29 09:59:05', '2022-07-29 09:59:05', 0);
INSERT INTO `trans_flow` VALUES (67, 8, 'jack', '20220730152924169', 1, '充值', 1000000.00, '充值', '2022-07-30 07:29:27', '2022-07-30 07:29:27', 0);
INSERT INTO `trans_flow` VALUES (68, 10, '李林', '20220730153641916', 1, '充值', 500000.00, '充值', '2022-07-30 07:36:44', '2022-07-30 07:36:44', 0);
INSERT INTO `trans_flow` VALUES (69, 12, '张九九', '20220730154320219', 1, '充值', 2000000.00, '充值', '2022-07-30 07:43:24', '2022-07-30 07:43:24', 0);
INSERT INTO `trans_flow` VALUES (70, 13, '李八吧', '20220730154532948', 1, '充值', 400000.00, '充值', '2022-07-30 07:45:34', '2022-07-30 07:45:34', 0);
INSERT INTO `trans_flow` VALUES (71, 12, '张九九', 'INVEST20220730154903301', 2, '投标锁定', 20000.00, '投资项目编号：LEND20220730154841823,项目名称：装修贷001', '2022-07-30 07:49:06', '2022-07-30 07:49:06', 0);
INSERT INTO `trans_flow` VALUES (72, 12, '张九九', 'INVEST20220730155031509', 2, '投标锁定', 40000.00, '投资项目编号：LEND20220730154841823,项目名称：装修贷001', '2022-07-30 07:50:36', '2022-07-30 07:50:36', 0);
INSERT INTO `trans_flow` VALUES (73, 7, '凡凡', '20220730155339423', 1, '充值', 99999.00, '充值', '2022-07-30 07:53:41', '2022-07-30 07:53:41', 0);
INSERT INTO `trans_flow` VALUES (74, 7, '凡凡', 'INVEST20220730155452405', 2, '投标锁定', 40000.00, '投资项目编号：LEND20220730154841823,项目名称：装修贷001', '2022-07-30 07:54:54', '2022-07-30 07:54:54', 0);
INSERT INTO `trans_flow` VALUES (83, 13, '李八吧', 'LOAN20220730155734412', 5, '放款到账', 100000.00, '项目放款，项目编号：LEND20220730154841823，项目名称：装修贷001', '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `trans_flow` VALUES (84, 12, '张九九', 'TRANS20220730155734540', 3, '放款解锁', 20000.00, '项目放款，冻结资金转出，项目编号：LEND20220730154841823，项目名称：装修贷001', '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `trans_flow` VALUES (85, 12, '张九九', 'TRANS20220730155734609', 3, '放款解锁', 40000.00, '项目放款，冻结资金转出，项目编号：LEND20220730154841823，项目名称：装修贷001', '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `trans_flow` VALUES (86, 7, '凡凡', 'TRANS20220730155734840', 3, '放款解锁', 40000.00, '项目放款，冻结资金转出，项目编号：LEND20220730154841823，项目名称：装修贷001', '2022-07-30 07:57:33', '2022-07-30 07:57:33', 0);
INSERT INTO `trans_flow` VALUES (87, 7, '凡凡', '20220730164853278', 1, '充值', 1000.00, '充值', '2022-07-30 08:48:56', '2022-07-30 08:48:56', 0);

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '帐户可用余额',
  `freeze_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (7, 7, 64999.00, 0.00, '2022-07-26 03:44:34', '2022-07-30 08:48:56', 0, 0);
INSERT INTO `user_account` VALUES (8, 8, 1000000.00, 0.00, '2022-07-26 07:21:07', '2022-07-30 07:29:27', 0, 0);
INSERT INTO `user_account` VALUES (9, 9, 0.00, 0.00, '2022-07-26 07:21:41', '2022-07-26 07:21:41', 0, 0);
INSERT INTO `user_account` VALUES (10, 10, 506010.00, 0.00, '2022-07-26 07:22:17', '2022-07-30 07:36:44', 0, 0);
INSERT INTO `user_account` VALUES (11, 11, 0.00, 0.00, '2022-07-26 07:23:56', '2022-07-26 07:23:56', 0, 0);
INSERT INTO `user_account` VALUES (12, 12, 1940000.00, 0.00, '2022-07-30 07:41:55', '2022-07-30 07:57:33', 0, 0);
INSERT INTO `user_account` VALUES (13, 13, 500000.00, 0.00, '2022-07-30 07:44:14', '2022-07-30 07:57:33', 0, 0);

-- ----------------------------
-- Table structure for user_bind
-- ----------------------------
DROP TABLE IF EXISTS `user_bind`;
CREATE TABLE `user_bind`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '身份证号',
  `bank_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行类型',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定账户协议号',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户绑定表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_bind
-- ----------------------------
INSERT INTO `user_bind` VALUES (9, 7, '凡凡', '4201022222222362', '5622222222222', '招商银行', '13433333333', '00ad93e70341467b83991795d4a45e21', 1, '2022-07-27 07:14:06', '2022-07-27 07:14:06', 0);
INSERT INTO `user_bind` VALUES (10, 10, '李林', '523623124578965456', '653232315648793', '中国银行', '13535369896', '0b1dda6dbf94441ab1d2b420f3ddebb1', 1, '2022-07-28 02:40:56', '2022-07-28 02:40:56', 0);
INSERT INTO `user_bind` VALUES (11, 8, 'jack', '4855634365986543', '6865979856233', '工商银行', '13456896531', '187235a6db104bc29812f7f700600733', 1, '2022-07-30 07:28:41', '2022-07-30 07:28:41', 0);
INSERT INTO `user_bind` VALUES (12, 12, '张九九', '5656235632924533', '652323652362396', '浦发银行', '13439999999', 'be694b0593884a77aa118653b44f2f99', 1, '2022-07-30 07:42:43', '2022-07-30 07:42:43', 0);
INSERT INTO `user_bind` VALUES (13, 13, '李八吧', '987654321453013', '356324563215454568', '日本银行', '13438888888', 'b00643d8f3654f40834c2e15e2dd9dff', 1, '2022-07-30 07:45:05', '2022-07-30 07:45:05', 0);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '1：出借人 2：借款人',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `openid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信用户标识openid',
  `head_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `bind_status` tinyint NOT NULL DEFAULT 0 COMMENT '绑定状态（0：未绑定，1：绑定成功 -1：绑定失败）',
  `borrow_auth_status` tinyint NOT NULL DEFAULT 0 COMMENT '借款人认证状态（0：未认证 1：认证中 2：认证通过 -1：认证失败）',
  `bind_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定账户协议号',
  `integral` int NOT NULL DEFAULT 0 COMMENT '用户积分',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（0：锁定 1：正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_mobile`(`mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户基本信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (7, 1, '13436596835', 'e10adc3949ba59abbe56e057f20f883e', '13436596835', '凡凡', '410621198111167629', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 1, 0, '00ad93e70341467b83991795d4a45e21', 0, 1, '2022-07-26 03:44:34', '2022-07-27 10:44:34', 0);
INSERT INTO `user_info` VALUES (8, 2, '13437587875', 'e10adc3949ba59abbe56e057f20f883e', '13437587875', 'jack', '4855634365986543', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 1, 2, '187235a6db104bc29812f7f700600733', 290, 1, '2022-07-26 07:21:07', '2022-07-27 10:44:34', 0);
INSERT INTO `user_info` VALUES (9, 1, '13435569568', 'e10adc3949ba59abbe56e057f20f883e', '13435569568', '13435569568', '510824199505076912', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 0, 0, NULL, 0, 1, '2022-07-26 07:21:41', '2022-07-27 10:44:34', 0);
INSERT INTO `user_info` VALUES (10, 2, '13447659635', 'e10adc3949ba59abbe56e057f20f883e', '13447659635', '李林', '523623124578965456', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 1, 2, '0b1dda6dbf94441ab1d2b420f3ddebb1', 270, 1, '2022-07-26 07:22:17', '2022-07-27 10:44:34', 0);
INSERT INTO `user_info` VALUES (11, 1, '13456865256', 'e10adc3949ba59abbe56e057f20f883e', '13456865256', '13456865256', '440224198902247628', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 0, 0, NULL, 0, 1, '2022-07-26 07:23:56', '2022-07-27 10:44:34', 0);
INSERT INTO `user_info` VALUES (12, 1, '13439999999', 'e10adc3949ba59abbe56e057f20f883e', '13439999999', '张九九', '5656235632924533', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 1, 0, 'be694b0593884a77aa118653b44f2f99', 0, 1, '2022-07-30 07:41:55', '2022-07-30 07:41:55', 0);
INSERT INTO `user_info` VALUES (13, 2, '13438888888', 'e10adc3949ba59abbe56e057f20f883e', '13438888888', '李八吧', '987654321453013', NULL, NULL, 'http://oss.shuhongfan.cf/shf.jpg', 1, 2, 'b00643d8f3654f40834c2e15e2dd9dff', 290, 1, '2022-07-30 07:44:14', '2022-07-30 07:44:14', 0);

-- ----------------------------
-- Table structure for user_integral
-- ----------------------------
DROP TABLE IF EXISTS `user_integral`;
CREATE TABLE `user_integral`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `integral` int NULL DEFAULT NULL COMMENT '积分',
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '获取积分说明',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户积分记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_integral
-- ----------------------------
INSERT INTO `user_integral` VALUES (21, 8, 100, '借款人基本信息', '2022-07-27 10:33:15', '2022-07-27 10:33:15', 0);
INSERT INTO `user_integral` VALUES (22, 8, 30, '借款人身份证信息', '2022-07-27 10:33:15', '2022-07-27 10:33:15', 0);
INSERT INTO `user_integral` VALUES (23, 8, 100, '借款人房产信息', '2022-07-27 10:33:15', '2022-07-27 10:33:15', 0);
INSERT INTO `user_integral` VALUES (24, 8, 60, '借款人车辆信息', '2022-07-27 10:33:15', '2022-07-27 10:33:15', 0);
INSERT INTO `user_integral` VALUES (25, 10, 80, '借款人基本信息', '2022-07-27 10:48:09', '2022-07-27 10:48:09', 0);
INSERT INTO `user_integral` VALUES (26, 10, 30, '借款人身份证信息', '2022-07-27 10:48:09', '2022-07-27 10:48:09', 0);
INSERT INTO `user_integral` VALUES (27, 10, 100, '借款人房产信息', '2022-07-27 10:48:09', '2022-07-27 10:48:09', 0);
INSERT INTO `user_integral` VALUES (28, 10, 60, '借款人车辆信息', '2022-07-27 10:48:09', '2022-07-27 10:48:09', 0);
INSERT INTO `user_integral` VALUES (29, 13, 100, '借款人基本信息', '2022-07-30 07:47:41', '2022-07-30 07:47:41', 0);
INSERT INTO `user_integral` VALUES (30, 13, 30, '借款人身份证信息', '2022-07-30 07:47:41', '2022-07-30 07:47:41', 0);
INSERT INTO `user_integral` VALUES (31, 13, 100, '借款人房产信息', '2022-07-30 07:47:41', '2022-07-30 07:47:41', 0);
INSERT INTO `user_integral` VALUES (32, 13, 60, '借款人车辆信息', '2022-07-30 07:47:41', '2022-07-30 07:47:41', 0);

-- ----------------------------
-- Table structure for user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `user_login_record`;
CREATE TABLE `user_login_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_login_record
-- ----------------------------
INSERT INTO `user_login_record` VALUES (29, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:18:50', '2022-07-26 04:18:50', 0);
INSERT INTO `user_login_record` VALUES (30, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:19:54', '2022-07-26 04:19:54', 0);
INSERT INTO `user_login_record` VALUES (31, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:22:08', '2022-07-26 04:22:08', 0);
INSERT INTO `user_login_record` VALUES (32, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:23:20', '2022-07-26 04:23:20', 0);
INSERT INTO `user_login_record` VALUES (33, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:27:34', '2022-07-26 04:27:34', 0);
INSERT INTO `user_login_record` VALUES (34, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:37:32', '2022-07-26 04:37:32', 0);
INSERT INTO `user_login_record` VALUES (35, 7, '0:0:0:0:0:0:0:1', '2022-07-26 04:41:59', '2022-07-26 04:41:59', 0);
INSERT INTO `user_login_record` VALUES (36, 8, '192.168.120.1', '2022-07-26 10:07:23', '2022-07-26 10:07:23', 0);
INSERT INTO `user_login_record` VALUES (37, 7, '192.168.120.1', '2022-07-27 07:12:53', '2022-07-27 07:12:53', 0);
INSERT INTO `user_login_record` VALUES (38, 8, '192.168.120.1', '2022-07-27 07:26:33', '2022-07-27 07:26:33', 0);
INSERT INTO `user_login_record` VALUES (39, 10, '192.168.120.1', '2022-07-27 10:47:07', '2022-07-27 10:47:07', 0);
INSERT INTO `user_login_record` VALUES (40, 10, '192.168.120.1', '2022-07-28 11:40:38', '2022-07-28 11:40:38', 0);
INSERT INTO `user_login_record` VALUES (41, 7, '192.168.120.1', '2022-07-29 06:53:14', '2022-07-29 06:53:14', 0);
INSERT INTO `user_login_record` VALUES (42, 7, '192.168.120.1', '2022-07-29 06:53:27', '2022-07-29 06:53:27', 0);
INSERT INTO `user_login_record` VALUES (43, 8, '192.168.120.1', '2022-07-30 06:57:31', '2022-07-30 06:57:31', 0);
INSERT INTO `user_login_record` VALUES (44, 10, '192.168.120.1', '2022-07-30 07:35:25', '2022-07-30 07:35:25', 0);
INSERT INTO `user_login_record` VALUES (45, 10, '192.168.120.1', '2022-07-30 07:35:59', '2022-07-30 07:35:59', 0);
INSERT INTO `user_login_record` VALUES (46, 12, '192.168.120.1', '2022-07-30 07:42:09', '2022-07-30 07:42:09', 0);
INSERT INTO `user_login_record` VALUES (47, 13, '192.168.120.1', '2022-07-30 07:44:25', '2022-07-30 07:44:25', 0);
INSERT INTO `user_login_record` VALUES (48, 7, '192.168.120.1', '2022-07-30 07:51:55', '2022-07-30 07:51:55', 0);
INSERT INTO `user_login_record` VALUES (49, 7, '192.168.120.1', '2022-07-30 07:52:06', '2022-07-30 07:52:06', 0);
INSERT INTO `user_login_record` VALUES (50, 7, '192.168.120.1', '2022-07-30 07:52:09', '2022-07-30 07:52:09', 0);
INSERT INTO `user_login_record` VALUES (51, 7, '192.168.120.1', '2022-07-30 07:52:10', '2022-07-30 07:52:10', 0);
INSERT INTO `user_login_record` VALUES (52, 7, '192.168.120.1', '2022-07-30 07:52:25', '2022-07-30 07:52:25', 0);

SET FOREIGN_KEY_CHECKS = 1;
