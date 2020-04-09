/*
 Navicat Premium Data Transfer

 Source Server         : 泉州开发环境5.10-3306
 Source Server Type    : MySQL
 Source Server Version : 50636
 Source Host           : 192.168.5.10:3306
 Source Schema         : unit_auth

 Target Server Type    : MySQL
 Target Server Version : 50636
 File Encoding         : 65001

 Date: 25/12/2018 11:31:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_third_user
-- ----------------------------
DROP TABLE IF EXISTS `t_third_user`;
CREATE TABLE `t_third_user`  (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plateform_type` int(2) NULL DEFAULT NULL COMMENT '0 通天星 1锐明',
  `server_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户绑定第三方账号所在服务器ip',
  `server_port` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户绑定第三方账号所在服务器端口',
  `third_user_name` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定第三方账号',
  `third_password` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定第三方账号密码',
  `third_token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方服务登录认证令牌',
  `auth_expire` datetime(0) NULL DEFAULT NULL COMMENT '第三方认证令牌到期时间',
  `auth_time` datetime(0) NULL DEFAULT NULL COMMENT '获取认证令牌时间',
  `userid` bigint(20) NOT NULL COMMENT '绑定第三方用户的统一认证服务用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '统一认证平台用户绑定的第三方用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `password` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `nick_name` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户昵称',
  `del_flag` int(2) NULL DEFAULT NULL COMMENT '删除标志 0未删除  -1已删除',
  `reg_date` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `phone` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `e_mail` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '统一认证平台下用户信息表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
