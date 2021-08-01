/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : imooc-security

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 01/08/2021 17:54:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for audit_log
-- ----------------------------
DROP TABLE IF EXISTS `audit_log`;
CREATE TABLE `audit_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modify_time` datetime(6) NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit_log
-- ----------------------------
INSERT INTO `audit_log` VALUES (10, '2021-07-18 16:36:02.824000', 'POST', '2021-07-18 16:36:02.874000', '/users', 403, 'admin');
INSERT INTO `audit_log` VALUES (11, '2021-07-18 16:38:11.343000', 'POST', '2021-07-18 16:38:11.354000', '/users', 401, 'admin');
INSERT INTO `audit_log` VALUES (12, '2021-07-18 16:38:26.950000', 'GET', '2021-07-18 16:38:26.958000', '/users/4', 401, 'admin');
INSERT INTO `audit_log` VALUES (13, '2021-07-18 17:00:54.036000', 'GET', '2021-07-18 17:01:20.422000', '/users/4', 401, 'admin');
INSERT INTO `audit_log` VALUES (14, '2021-07-18 17:01:58.350000', 'GET', '2021-07-18 17:02:09.040000', '/users/login', 200, 'admin');
INSERT INTO `audit_log` VALUES (15, '2021-07-18 17:02:53.200000', 'GET', '2021-07-18 17:02:55.718000', '/users/4', 401, 'admin');
INSERT INTO `audit_log` VALUES (16, '2021-07-18 17:34:29.683000', 'POST', '2021-07-18 17:34:31.453000', '/users', 401, NULL);
INSERT INTO `audit_log` VALUES (17, '2021-07-18 17:34:41.058000', 'POST', '2021-07-18 17:34:41.068000', '/users', 403, 'vm1');
INSERT INTO `audit_log` VALUES (18, '2021-07-18 17:34:49.518000', 'GET', '2021-07-18 17:34:49.527000', '/users/4', 401, NULL);
INSERT INTO `audit_log` VALUES (19, '2021-07-18 17:35:02.716000', 'GET', '2021-07-18 17:35:02.799000', '/users/4', 200, 'vm2');
INSERT INTO `audit_log` VALUES (20, '2021-07-18 17:35:15.273000', 'GET', '2021-07-18 17:35:15.282000', '/users/4', 401, NULL);
INSERT INTO `audit_log` VALUES (21, '2021-07-18 17:35:19.002000', 'GET', '2021-07-18 17:35:19.016000', '/users/4', 200, 'vm2');
INSERT INTO `audit_log` VALUES (22, '2021-07-18 17:35:27.525000', 'GET', '2021-07-18 17:35:31.485000', '/users/login', 500, NULL);
INSERT INTO `audit_log` VALUES (23, '2021-07-18 17:36:14.576000', 'GET', '2021-07-18 17:36:54.307000', '/users/login', 200, NULL);
INSERT INTO `audit_log` VALUES (24, '2021-07-18 17:36:44.168000', 'GET', '2021-07-18 17:36:44.697000', '/users/login', 200, NULL);
INSERT INTO `audit_log` VALUES (25, '2021-07-18 17:36:50.109000', 'GET', '2021-07-18 17:36:50.522000', '/users/login', 200, 'vm3');
INSERT INTO `audit_log` VALUES (26, '2021-07-18 17:37:10.837000', 'GET', '2021-07-18 17:38:01.227000', '/users/login', 200, 'vm3');
INSERT INTO `audit_log` VALUES (27, '2021-07-18 17:38:14.278000', 'GET', '2021-07-18 17:38:14.290000', '/users/4', 403, 'vm3');
INSERT INTO `audit_log` VALUES (28, '2021-07-18 17:38:53.989000', 'GET', '2021-07-18 17:38:58.620000', '/users/4', 403, 'vm3');
INSERT INTO `audit_log` VALUES (29, '2021-07-18 17:39:01.646000', 'GET', '2021-07-18 17:39:17.880000', '/users/4', 403, 'vm3');
INSERT INTO `audit_log` VALUES (30, '2021-07-18 17:39:22.318000', 'GET', '2021-07-18 17:40:56.851000', '/users/login', 200, 'vm3');
INSERT INTO `audit_log` VALUES (31, '2021-07-18 17:41:03.454000', 'GET', '2021-07-18 17:41:05.806000', '/users/4', 500, 'vm3');
INSERT INTO `audit_log` VALUES (32, '2021-07-18 17:41:10.171000', 'GET', '2021-07-18 17:41:18.776000', '/users/4', 500, 'vm3');
INSERT INTO `audit_log` VALUES (33, '2021-07-18 17:41:31.130000', 'GET', '2021-07-18 17:41:45.547000', '/users/4', 500, 'vm3');
INSERT INTO `audit_log` VALUES (34, '2021-07-18 17:41:58.272000', 'GET', '2021-07-18 17:42:00.858000', '/users/5', 200, 'vm3');
INSERT INTO `audit_log` VALUES (35, '2021-07-18 17:43:03.072000', 'GET', '2021-07-18 17:43:04.709000', '/users/5', 200, 'vm3');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `expiresAt` datetime NULL DEFAULT NULL,
  `lastModifiedAt` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('orderApp', 'service-order', '$2a$10$xVVTU.aMD1mjm1aHnyve0eFHjsJ6L6V28mEMbfCwl9NTgozVw09Ia', 'read,write', 'password', NULL, NULL, 3600, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('orderService', 'service-order', '$2a$10$ZF9ioe/YCbFz.Es853p4dO5xeyC0DyPAveSguZp0s4dyRQv5BzMke', 'read', 'password', NULL, NULL, 3600, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session`  (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`) USING BTREE,
  UNIQUE INDEX `SPRING_SESSION_IX1`(`SESSION_ID`) USING BTREE,
  INDEX `SPRING_SESSION_IX2`(`EXPIRY_TIME`) USING BTREE,
  INDEX `SPRING_SESSION_IX3`(`PRINCIPAL_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spring_session
-- ----------------------------

-- ----------------------------
-- Table structure for spring_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE `spring_session_attributes`  (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`, `ATTRIBUTE_NAME`) USING BTREE,
  INDEX `SPRING_SESSION_ATTRIBUTES_IX1`(`SESSION_PRIMARY_ID`) USING BTREE,
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of spring_session_attributes
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permissions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_sb8bbouer5wak8vyiiy4pf2bx`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'vm1', '$s0$f0801$dIo5kpkeffGyD2qIRmdydQ==$+h71x0CRKa3iGRsLM6pWXx9NB1GfeFFfc8r3862zFWI=', 'vm1', 'r');
INSERT INTO `user` VALUES (4, 'vm2', '$s0$f0801$ZpNwR+qYtBkHQzVDqVc2/A==$swPoHCu/qdN/610fV7WFcp7J+dUAN1q+isu27/QKHeY=', 'vm2', 'rw');
INSERT INTO `user` VALUES (5, 'vm3', '$s0$f0801$gHyfChJ5I+fJ1P3zGH66/w==$DYomjkmWTLPMdxd4hW2cbPeKkX9UgDqJ1Bh9JsxQeKI=', 'vm3', 'rw');

SET FOREIGN_KEY_CHECKS = 1;
