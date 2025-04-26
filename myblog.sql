/*
 Navicat Premium Dump SQL

 Source Server         : home-service-192.168.31.100
 Source Server Type    : MySQL
 Source Server Version : 80024 (8.0.24)
 Source Host           : 192.168.31.100:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 80024 (8.0.24)
 File Encoding         : 65001

 Date: 26/04/2025 18:57:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `full_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `source_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `categories` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `is_top` tinyint NULL DEFAULT 0,
  `is_original` tinyint NULL DEFAULT 1,
  `is_public` tinyint NULL DEFAULT 0,
  `view_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_comment` tinyint NULL DEFAULT 1,
  `is_link` tinyint NULL DEFAULT 0,
  `is_private` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `banner_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `father_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'root',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`  (
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `end_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `upload_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `source_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `download_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci GENERATED ALWAYS AS (concat(_utf8mb4'/files/',`uuid`,`end_name`)) STORED NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of files
-- ----------------------------

-- ----------------------------
-- Table structure for photo
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `images` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `index_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_public` tinyint NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of photo
-- ----------------------------

-- ----------------------------
-- Table structure for resume
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `icon` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'row',
  `min_width` int NULL DEFAULT 0,
  `max_width` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` tinyint NULL DEFAULT 1,
  `section` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ordernum` int NULL DEFAULT NULL,
  `isopen` tinyint NULL DEFAULT 0,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resume
-- ----------------------------

-- ----------------------------
-- Table structure for resumesection
-- ----------------------------
DROP TABLE IF EXISTS `resumesection`;
CREATE TABLE `resumesection`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint NULL DEFAULT 1,
  `section` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isrow` tinyint NULL DEFAULT 0,
  `ordernum` int NULL DEFAULT NULL,
  `min_width` int NULL DEFAULT 0,
  `max_width` int NULL DEFAULT 100,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resumesection
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `banner_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('225386e376a54bca8206e6efa8b0bf0c', 'admin', '管理员', '123456', '2024-07-22 14:20:58', '2025-04-26 18:56:46');

-- ----------------------------
-- Table structure for website
-- ----------------------------
DROP TABLE IF EXISTS `website`;
CREATE TABLE `website`  (
  `uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_num` int NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of website
-- ----------------------------
INSERT INTO `website` VALUES ('2f3f9008ac3f462bb1960cbb47a3418d', 'func_notice', '闭站公告', '{\"value\":\"<p style=\\\"text-align: center;\\\">&nbsp;</p>\\n<p style=\\\"text-align: center;\\\">&nbsp;</p>\\n<p style=\\\"text-align: center;\\\"><img src=\\\"files/2caf7ed0fb3144dcbf199f597d36c423.jpg\\\" width=\\\"128\\\" height=\\\"128\\\"></p>\\n<p style=\\\"text-align: center;\\\">&nbsp;</p>\\n<p style=\\\"text-align: center;\\\"><span style=\\\"font-size: 32px;\\\">网站维护中</span></p>\"}', NULL, '2024-08-31 22:42:27', '2024-08-31 22:45:26', 2);
INSERT INTO `website` VALUES ('3792bdacd73e40f0b912289e8df57df8', 'web_navigate', '网站导航', '{\"value\":[{\"nav_title\":\"首页\",\"nav_subtitle\":\"\",\"nav_url\":\"/\",\"nav_name\":\"🏠 首页\",\"nav_enabled\":true,\"nav_banner\":[]},{\"nav_title\":\"📒归档\",\"nav_subtitle\":\"\",\"nav_url\":\"/archive\",\"nav_name\":\"📒 归档\",\"nav_enabled\":true,\"nav_banner\":[\"/files/dae595a7ba534250afde22d16bec312b.jpg\"]},{\"nav_title\":\"🏞️25号底片\",\"nav_subtitle\":\"二十五号底片\",\"nav_url\":\"/photo\",\"nav_name\":\"🏞️ 25号底片\",\"nav_enabled\":true,\"nav_banner\":[\"/files/ffa67a6b734c4f5999e26bc2c64c9d36.jpg\"]},{\"nav_title\":\"🌈关于\",\"nav_subtitle\":\"\",\"nav_url\":\"/about\",\"nav_name\":\"🌈 关于\",\"nav_enabled\":false,\"nav_banner\":[]},{\"nav_title\":\"🏷️简历\",\"nav_subtitle\":\"\",\"nav_url\":\"/resume\",\"nav_name\":\"🏷️ 简历\",\"nav_enabled\":true,\"nav_banner\":[\"/files/dae595a7ba534250afde22d16bec312b.jpg\"]},{\"nav_name\":\"zjweu\",\"nav_title\":\"\",\"nav_subtitle\":\"\",\"nav_url\":\"https://www.zjweu.edu.cn\",\"nav_enabled\":false,\"nav_banner\":[]},{\"nav_name\":\"🗂 Alist\",\"nav_title\":\"\",\"nav_subtitle\":\"\",\"nav_url\":\"https://alist.moujitx.cn/\",\"nav_enabled\":true,\"nav_banner\":[]}]}', '导航名称、网址、副标题', '2024-08-30 20:45:42', '2024-09-01 00:02:11', 7);
INSERT INTO `website` VALUES ('3cedc0ca5c70496ebbb0313ee349360d', 'profile_slogan', '个人标签', '{\"value\":\"广阔天地，大有作为\"}', NULL, '2024-09-07 01:19:35', '2024-09-07 01:20:27', 2);
INSERT INTO `website` VALUES ('4bb0bca816a74dc5ab56bcf9cd74017f', 'web_icp', 'ICP备案', '{\"value\":\"浙ICP备2024116501号\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:40', 5);
INSERT INTO `website` VALUES ('6403daee7fde467fbc491001c1125a1d', 'web_name', '网站名称', '{\"value\":\"某季同学\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:39', 1);
INSERT INTO `website` VALUES ('727a45b15c234d39a12374f923026b85', 'web_subtitle', '副标题', '{\"value\":\"A vast world, a great achievements.\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:18', 4);
INSERT INTO `website` VALUES ('77c0db1d81dc4c839ea5880bce0d888f', 'profile_background', '个人背景', '{\"value\":\"/files/d321968551c3468b818d12e98629c199.jpg\"}', NULL, '2024-08-30 20:45:42', '2024-09-07 01:20:39', 5);
INSERT INTO `website` VALUES ('83d8c444f590496783a47e1035c41058', 'func_grey', '灰色模式', '{\"value\":true}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:14:52', 2);
INSERT INTO `website` VALUES ('8edc5de44ba541d98d65ba07e43cabac', 'web_banner', '首页背景', '{\"value\":[\"/files/ffa67a6b734c4f5999e26bc2c64c9d36.jpg\",\"/files/dae595a7ba534250afde22d16bec312b.jpg\",\"/files/cfb87880e9a04164a2571d412fd833f2.png\",\"/files/12d4c203151a4d15b136cdc8e5890e5d.png\",\"/files/002c280803b942718210ac51c08456f0.png\"]}', NULL, '2024-09-01 00:13:17', '2024-09-01 00:15:00', 8);
INSERT INTO `website` VALUES ('969547773af04a888374de6735e62f19', 'func_enabled', '网站启用', '{\"value\":true}', NULL, '2024-08-30 22:22:06', '2024-08-31 22:45:22', 1);
INSERT INTO `website` VALUES ('a0e5dd1e8bf54ee09ed298afbdda92f6', 'profile_nick', '个人昵称', '{\"value\":\"MOUJITX\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:41', 1);
INSERT INTO `website` VALUES ('b670ee50810b4015bec6edbefdbdf610', 'func_comment', '全局评论', '{\"value\":false}', NULL, '2024-08-30 20:45:42', '2024-08-31 22:44:35', 4);
INSERT INTO `website` VALUES ('b9999fa0a64d4deb93735c3e5e288ad6', 'profile_concat', '联系方式', '{\"value\":[{\"cc_icon\":\"/files/34839c9cd47543cfa5d6c3df62ced36a.svg\",\"cc_name\":\"Bilibili\",\"cc_value\":\"\",\"cc_url\":\"https://space.bilibili.com/22738512\"},{\"cc_icon\":\"/files/4712972d8fe24ec48a519036035205c1.svg\",\"cc_name\":\"Github\",\"cc_value\":\"\",\"cc_url\":\"https://github.com/moujitx\"}]}', NULL, '2024-08-30 20:45:42', '2024-09-07 01:20:38', 4);
INSERT INTO `website` VALUES ('bf8cb137e6c94c1ca101ffdcea5065bc', 'func_search', '全局搜索', '{\"value\":false}', NULL, '2024-08-30 20:45:42', '2024-08-31 22:44:36', 5);
INSERT INTO `website` VALUES ('c54bc414df354661a26d0e2dd99c777a', 'profile_avatar', '个人头像', '{\"value\":\"/files/3c02b44a88d947b580103e8cec4495f8.jpg\"}', NULL, '2024-08-30 20:45:42', '2024-09-07 01:20:36', 3);
INSERT INTO `website` VALUES ('cff914c1f3d5475ab50a3fdd346ecfb1', 'web_title', '主标题', '{\"value\":\"广阔天地，大有作为\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:09', 3);
INSERT INTO `website` VALUES ('e4fdd1692a4a4f42a5884c95354e24ee', 'web_police', '公安备案', '{\"value\":\"\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:38', 6);
INSERT INTO `website` VALUES ('f54a08a3ba98456e86f9b83c58c75854', 'web_icon', '网站图标', '{\"value\":\"/files/3c02b44a88d947b580103e8cec4495f8.jpg\"}', NULL, '2024-08-30 20:45:42', '2024-08-31 21:05:40', 2);

SET FOREIGN_KEY_CHECKS = 1;
