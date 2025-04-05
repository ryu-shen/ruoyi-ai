/*
 Navicat Premium Data Transfer

 Source Server         : 本地_root_MySQL_8
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : ruoyi-ai-open

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 05/04/2025 13:01:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agi_app
-- ----------------------------
DROP TABLE IF EXISTS `agi_app`;
CREATE TABLE `agi_app`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `model_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联模型',
  `knowledge_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联知识库',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '提示词',
  `des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `save_time` datetime(0) NULL DEFAULT NULL COMMENT '保存时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_app
-- ----------------------------
INSERT INTO `agi_app` VALUES ('e16a582b47d3041cf14074d5451dff7a', '0c21c2f8ebd3aa3757ef1bae81154cc4', '[\"393704ac13f67fde5da674ddd0742b03\"]', '', 'RuoYi-AI官方应用', '你是一个专业的文档分析师，你擅长从文档中提取关键内容并总结分析含义，下面你需要根据用户的问题做出解答。\n\n## 限制\n不要回答和文档无关的内容', '快速解答RuoYi-AI项目相关的内容，RuoYi-AI官方助手', '2025-04-02 08:55:27', '0', NULL, NULL, '2024-08-04 17:49:24', NULL, '2025-04-02 08:55:27');

-- ----------------------------
-- Table structure for agi_app_api
-- ----------------------------
DROP TABLE IF EXISTS `agi_app_api`;
CREATE TABLE `agi_app_api`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `app_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用ID',
  `channel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用渠道',
  `api_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Key',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用api表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_app_api
-- ----------------------------

-- ----------------------------
-- Table structure for agi_conversation
-- ----------------------------
DROP TABLE IF EXISTS `agi_conversation`;
CREATE TABLE `agi_conversation`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `prompt_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提示词ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '对话窗口表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_conversation
-- ----------------------------

-- ----------------------------
-- Table structure for agi_docs
-- ----------------------------
DROP TABLE IF EXISTS `agi_docs`;
CREATE TABLE `agi_docs`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `origin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容或链接',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `slice_num` int(11) NULL DEFAULT NULL COMMENT '切片数量',
  `slice_status` tinyint(1) NULL DEFAULT NULL COMMENT '切片状态',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文档表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_docs
-- ----------------------------

-- ----------------------------
-- Table structure for agi_docs_slice
-- ----------------------------
DROP TABLE IF EXISTS `agi_docs_slice`;
CREATE TABLE `agi_docs_slice`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `vector_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '向量库的ID',
  `docs_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文档ID',
  `knowledge_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文档名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '切片内容',
  `word_num` int(11) NULL DEFAULT NULL COMMENT '字符数',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文档切片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_docs_slice
-- ----------------------------

-- ----------------------------
-- Table structure for agi_embed_store
-- ----------------------------
DROP TABLE IF EXISTS `agi_embed_store`;
CREATE TABLE `agi_embed_store`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '别名',
  `provider` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商',
  `host` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `port` int(11) NULL DEFAULT NULL COMMENT '端口',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `database_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据库名称',
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表名称',
  `dimension` int(11) NULL DEFAULT NULL COMMENT '向量维数',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '向量库配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_embed_store
-- ----------------------------
INSERT INTO `agi_embed_store` VALUES ('24bdccdb44817ccca76276f426857295', 'milvus向量库', 'MILVUS', '127.0.0.1', 19530, NULL, NULL, 'langchain', 'langchain_store', 1024, '0', NULL, NULL, NULL, NULL, '2025-04-04 17:33:16');

-- ----------------------------
-- Table structure for agi_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `agi_knowledge`;
CREATE TABLE `agi_knowledge`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `embed_store_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '向量数据库ID',
  `embed_model_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '向量模型ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '知识库名称',
  `des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '知识库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_knowledge
-- ----------------------------
INSERT INTO `agi_knowledge` VALUES ('393704ac13f67fde5da674ddd0742b03', NULL, '24bdccdb44817ccca76276f426857295', '79bf51635fa0f4023eade4496909d3ae', 'RuoYi-AI文档', 'RuoYi-AI官方文档', NULL, '0', NULL, NULL, NULL, NULL, '2025-04-02 08:52:03');

-- ----------------------------
-- Table structure for agi_message
-- ----------------------------
DROP TABLE IF EXISTS `agi_message`;
CREATE TABLE `agi_message`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `conversation_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会话ID',
  `chat_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息的ID',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色，user和assistant',
  `model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '消息内容',
  `tokens` int(11) NULL DEFAULT NULL,
  `prompt_tokens` int(11) NULL DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `conversation_id`(`conversation_id`) USING BTREE,
  INDEX `role`(`role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '对话消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_message
-- ----------------------------

-- ----------------------------
-- Table structure for agi_model
-- ----------------------------
DROP TABLE IF EXISTS `agi_model`;
CREATE TABLE `agi_model`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型: CHAT、Embedding、Image',
  `model` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `provider` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '别名',
  `response_limit` int(11) NULL DEFAULT NULL COMMENT '响应长度',
  `temperature` double NULL DEFAULT NULL COMMENT '温度',
  `top_p` double NULL DEFAULT NULL,
  `api_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `base_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `endpoint` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `azure_deployment_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'azure模型参数',
  `gemini_project` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'gemini模型参数',
  `gemini_location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'gemini模型参数',
  `image_size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片大小',
  `image_quality` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片质量',
  `image_style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片风格',
  `dimension` int(11) NULL DEFAULT NULL COMMENT '向量维数',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_dept` bigint(20) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'LLM模型配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_model
-- ----------------------------
INSERT INTO `agi_model` VALUES ('5cc275f4e4dcd436ece893e50ab0ade5', 'CHAT', 'deepseek-r1', 'OLLAMA', 'deepseek-r1', 2000, 0.2, 0.8, NULL, 'http://127.0.0.1:11434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, '2025-03-31 14:55:40', NULL, '2025-03-31 14:55:40');
INSERT INTO `agi_model` VALUES ('79bf51635fa0f4023eade4496909d3ae', 'EMBEDDING', 'bge-m3', 'OLLAMA', 'bge-m3', NULL, 0.2, 0, NULL, 'http://127.0.0.1:11434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1024, '0', NULL, NULL, '2025-03-31 14:56:50', NULL, '2025-03-31 14:56:50');

-- ----------------------------
-- Table structure for agi_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_dept`;
CREATE TABLE `agi_sys_dept`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门ID',
  `parent_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级部门ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `order_no` int(11) NULL DEFAULT NULL COMMENT '排序',
  `des` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_dept
-- ----------------------------
INSERT INTO `agi_sys_dept` VALUES ('14b300858a898c6dcfd3dc95dde6df81', 'ece0a14ab891e775ff9f6252731130b7', '事业部', NULL, '事业部');
INSERT INTO `agi_sys_dept` VALUES ('16794f488aa3b6f77012749a8160f45e', 'e8017fb290f576f5e1f60be4ab4f166a', '前端研发团队', NULL, '前端研发团队');
INSERT INTO `agi_sys_dept` VALUES ('3f7ed841ec5e92ee039fd83bf3fd0ee4', '14b300858a898c6dcfd3dc95dde6df81', '北京事业部', NULL, '');
INSERT INTO `agi_sys_dept` VALUES ('87388f69e48e53c3771bbd2a56256374', '14b300858a898c6dcfd3dc95dde6df81', '销售团队', NULL, '销售团队');
INSERT INTO `agi_sys_dept` VALUES ('da6b0029262feb514ab8c70d7f72c2c7', 'e8017fb290f576f5e1f60be4ab4f166a', '后端研发团队', NULL, '后端研发团队');
INSERT INTO `agi_sys_dept` VALUES ('e8017fb290f576f5e1f60be4ab4f166a', 'ece0a14ab891e775ff9f6252731130b7', '产品研发部', NULL, '产品研发部');
INSERT INTO `agi_sys_dept` VALUES ('ece0a14ab891e775ff9f6252731130b7', '0', '董事会', 1, NULL);
INSERT INTO `agi_sys_dept` VALUES ('efcdacd7dc23901f8f7f05a47c452eaa', 'e8017fb290f576f5e1f60be4ab4f166a', '产品团队', NULL, NULL);

-- ----------------------------
-- Table structure for agi_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_log`;
CREATE TABLE `agi_sys_log`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `type` int(11) NULL DEFAULT NULL COMMENT '日志类型，1正常 2异常 ',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operation` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URL',
  `time` bigint(20) NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作方法',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作参数',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for agi_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_menu`;
CREATE TABLE `agi_sys_menu`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ID',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型',
  `order_no` int(11) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_disabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用',
  `is_ext` tinyint(1) NULL DEFAULT NULL COMMENT '是否外链',
  `is_keepalive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存',
  `is_show` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_menu
-- ----------------------------
INSERT INTO `agi_sys_menu` VALUES ('03917f40dfafba8c7ecb2b8843522a9e', '新增文档', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:docs:add', 'button', 10, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('0597ccbb7b98b2d443bffb3f1785ce1c', '新增知识库', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:knowledge:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('0976afe16e7b328886408f3e117733c1', '新增角色', '6f8aff1f2c458e5add9adb6d284fb451', NULL, 'upms:role:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('0adfa2c3c4d278aedd88019236c1425e', '向量库管理', '374409ab56141b311ccb0f1847dd724a', 'agi/embed-store', 'embedstore', 'menu', 1, '', '/agi/embed-store/index', 0, 0, 0, 1);
INSERT INTO `agi_sys_menu` VALUES ('0f37f45fb15c38de948b17b8a24e431b', '修改菜单', 'b1df787d8af5b728181a4b9acf38da93', NULL, 'upms:menu:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('1440895f54ccae1c1e2706e3dbcf6f5d', '文本向量化', '43563b039d30b990f87af37783115ff4', NULL, 'agi:embedding:text', 'button', 4, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('1c1fa2c50ff306144a0ea2528dcec96b', '重置密码', 'b29de942eeabc9419185951f57be11f3', NULL, 'upms:user:reset', 'button', 5, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('25be85ca9b16d482788365448e90643d', '删除配置', 'af34be481a0c679fc4b2e96922e8defc', NULL, 'system:oss:config:delete', 'button', 4, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('2dc3a6e16351901710060fd846ee9f19', '新增菜单', 'b1df787d8af5b728181a4b9acf38da93', NULL, 'upms:menu:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('2f5735d125b4537076893a4b4a37a188', '系统管理', '0', 'system', 'system', 'menu', 4, 'SettingsOutline', 'Layout', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('35dcd70c8a4008b554b71bf02ab07b61', '删除聊天记录', 'bdd70f2c1ee068c13bd3288eff07c8e2', NULL, 'chat:messages:clean', 'button', 3, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('374409ab56141b311ccb0f1847dd724a', 'AI平台', '0', '/agi', 'agi', 'menu', 2, 'CubeOutline', 'Layout', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('3d1700109ece0187ba5e76217cd71995', '删除对话数据', 'f1ad3c056ac91fa5292a99f223155afc', NULL, 'agi:message:delete', 'button', 2, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('43563b039d30b990f87af37783115ff4', 'AI应用管理', 'a2ccfe694cd91cf159ad35626e4ea202', 'list', 'agi:app', 'menu', 2, '', '/app/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('4488cb5271b1220647d4a83cfbcb7b15', '文档向量化', '43563b039d30b990f87af37783115ff4', NULL, 'agi:embedding:docs', 'button', 5, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('510a89f01571d7eaa3b1393c8534ab6f', '删除应用', '43563b039d30b990f87af37783115ff4', NULL, 'agi:app:delete', 'button', 3, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('51ed9b1f27acc4695667821eac5f35cb', '删除文档', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:docs:delete', 'button', 12, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('5514605bae6ffdad3e4acff3e9e9742c', '新增应用', '43563b039d30b990f87af37783115ff4', NULL, 'agi:app:add', 'button', 1, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('5ce2349dc38a84cfbf0f5b260b41a2b6', '模型管理', '374409ab56141b311ccb0f1847dd724a', 'model', 'model', 'menu', 0, '', '/agi/model/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('62beffe9252934b4adeeef3125cab584', '新增模型', '5ce2349dc38a84cfbf0f5b260b41a2b6', NULL, 'agi:model:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('64a1109e89e060bd7018806c62c8e7d3', '修改向量库', '0adfa2c3c4d278aedd88019236c1425e', NULL, 'agi:embedstore:update', 'button', 2, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('65deeb7aedec5490425ad2572d536ea9', 'Chat权限', '43563b039d30b990f87af37783115ff4', NULL, 'chat:completions', 'button', 6, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('67435b96a82c494b48fc6458b7103d4d', '页面预览', '43563b039d30b990f87af37783115ff4', NULL, 'chat-docs:view', 'button', 1, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('6c27a1ddba0ce10d7e242cb7e568bfc0', '删除模型', '5ce2349dc38a84cfbf0f5b260b41a2b6', NULL, 'agi:model:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('6cb25c77d3087d47a26c08d904a442fa', '新增部门', '8fb8756a4587cc4c76401a63ea194568', NULL, 'upms:dept:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('6f8aff1f2c458e5add9adb6d284fb451', '角色管理', '7c411c7d41034d6708103c8e0da19ced', 'role', 'role', 'menu', 2, NULL, '/upms/role/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('72215ec9609e546cd56bacf4c29e482d', '修改部门', '8fb8756a4587cc4c76401a63ea194568', NULL, 'upms:dept:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('757e0f3fb5e153c15f3355a97f731f1e', '删除向量库', '0adfa2c3c4d278aedd88019236c1425e', NULL, 'agi:embedstore:delete', 'button', 3, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('7b3e324f4470bbd4b8363b379fd3ed3c', '删除部门', '8fb8756a4587cc4c76401a63ea194568', NULL, 'upms:dept:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('7c411c7d41034d6708103c8e0da19ced', '权限管理', '0', 'upms', 'upms', 'menu', 3, 'KeyOutline', 'Layout', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('7d225cd8d60da156e17e341f86304970', '删除知识库', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:knowledge:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('80c1246cff10a470f67b4a58b0fe257e', '修改知识库', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:knowledge:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('89f1ba9a70e8bf72961f321156361fe6', '删除角色', '6f8aff1f2c458e5add9adb6d284fb451', NULL, 'upms:role:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('8b2924d753d4e2c1932e1f17e30d0c52', '修改模型', '5ce2349dc38a84cfbf0f5b260b41a2b6', NULL, 'agi:model:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('8c0eb60ccef367ce7048e5d486aaa3a9', '日志管理', '2f5735d125b4537076893a4b4a37a188', 'log', 'log', 'menu', 2, 'alert', '/system/log/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('8fb8756a4587cc4c76401a63ea194568', '部门管理', '7c411c7d41034d6708103c8e0da19ced', 'dept', 'dept', 'menu', 3, NULL, '/upms/dept/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('979631c0fae847a8dd59321b1da7d5e7', '新增用户', 'b29de942eeabc9419185951f57be11f3', NULL, 'upms:user:add', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('97a5eac3bfeeabe4013d828b919786f7', '知识库管理', '374409ab56141b311ccb0f1847dd724a', 'knowledge', 'knowledge', 'menu', 1, 'alert', '/agi/knowledge/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('9af13049237604d1260f1bbbc61a2aed', '新增配置', 'af34be481a0c679fc4b2e96922e8defc', NULL, 'system:oss:config:add', 'button', 2, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('9e526a34052ca76cf4f1ec685187e84a', '删除菜单', 'b1df787d8af5b728181a4b9acf38da93', NULL, 'upms:menu:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('a2ccfe694cd91cf159ad35626e4ea202', 'AI应用', '0', '/app', 'app', 'menu', 1, 'AppsOutline', 'Layout', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('a985c800d102da822b59dacc77ee6c9d', '修改用户', 'b29de942eeabc9419185951f57be11f3', NULL, 'upms:user:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('af34be481a0c679fc4b2e96922e8defc', 'OSS管理', '2f5735d125b4537076893a4b4a37a188', 'ossconfig', 'ossconfig', 'menu', 1, '', '/system/ossconfig/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('af8e11cdd57a935bbcf36f8e53cc889f', '新增向量库', '0adfa2c3c4d278aedd88019236c1425e', NULL, 'agi:embedstore:add', 'button', 1, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('b1df787d8af5b728181a4b9acf38da93', '菜单管理', '7c411c7d41034d6708103c8e0da19ced', 'menu', 'menu', 'menu', 4, NULL, '/upms/menu/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('b29de942eeabc9419185951f57be11f3', '用户管理', '7c411c7d41034d6708103c8e0da19ced', 'user', 'user', 'menu', 1, NULL, '/upms/user/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('b3331acdd06227088f3fb4b92b8b0365', '删除日志', '8c0eb60ccef367ce7048e5d486aaa3a9', NULL, 'system:log:delete', 'button', 2, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('bdd70f2c1ee068c13bd3288eff07c8e2', 'AI聊天助手', 'a2ccfe694cd91cf159ad35626e4ea202', 'chat', 'agi:chat', 'menu', 1, '', '/chat/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('c212381ae7a2333416a18e486f044777', '账单统计', '374409ab56141b311ccb0f1847dd724a', 'order', 'order', 'menu', 5, NULL, '/agi/order/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('c7f077cbfc37b0997679066e033ddb8e', '修改配置', 'af34be481a0c679fc4b2e96922e8defc', NULL, 'system:oss:config:update', 'button', 3, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('cac8d8f2f35bd872dcc3652add9bbd08', '修改角色', '6f8aff1f2c458e5add9adb6d284fb451', NULL, 'upms:role:update', 'button', 3, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('d99e460bd02a18eaf15206b09f709bfb', '修改应用', '43563b039d30b990f87af37783115ff4', NULL, 'agi:app:update', 'button', 2, NULL, NULL, 0, 0, 0, NULL);
INSERT INTO `agi_sys_menu` VALUES ('f1ad3c056ac91fa5292a99f223155afc', '对话数据', '374409ab56141b311ccb0f1847dd724a', 'message', 'message', 'menu', 4, NULL, '/agi/message/index', 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('f5031ca9ca645316c6eb94f4ea8684f8', '修改文档', '97a5eac3bfeeabe4013d828b919786f7', NULL, 'agi:docs:update', 'button', 11, NULL, NULL, 0, 0, 1, NULL);
INSERT INTO `agi_sys_menu` VALUES ('f5d6cbc1e97e2a87149598f86c1bdbbe', '删除用户', 'b29de942eeabc9419185951f57be11f3', NULL, 'upms:user:delete', 'button', 4, NULL, NULL, 0, 0, 1, 1);
INSERT INTO `agi_sys_menu` VALUES ('fadaa37669c31316d8addac152f1f0ff', '聊天权限', 'bdd70f2c1ee068c13bd3288eff07c8e2', NULL, 'chat:completions', 'button', 2, NULL, NULL, 0, 0, 0, NULL);

-- ----------------------------
-- Table structure for agi_sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_oss`;
CREATE TABLE `agi_sys_oss`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID',
  `oss_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `original_filename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始文件名称',
  `filename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件存储名称',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件地址',
  `base_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '桶路径',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件的绝对路径',
  `size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `ext` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `content_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件头',
  `platform` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '平台',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for agi_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_role`;
CREATE TABLE `agi_sys_role`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色别名',
  `des` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_role
-- ----------------------------
INSERT INTO `agi_sys_role` VALUES ('2827e950043adf67b7fe10306d3e94e4', '超级管理员角色', 'administrator', '超级管理员管理员，不受权限控制，不可编辑');
INSERT INTO `agi_sys_role` VALUES ('bbe1863be68ad07347b1dee0e358f18a', '默认人员角色', 'default_env', '后台新用户注册角色，不可删除');
INSERT INTO `agi_sys_role` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '演示环境角色', 'demo_env', '演示环境使用角色，拥有页面预览权限，没有操作权限');

-- ----------------------------
-- Table structure for agi_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_role_menu`;
CREATE TABLE `agi_sys_role_menu`  (
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_role_menu
-- ----------------------------
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '0825f18b3860f8c01a9b0d8221280e3b');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '0adfa2c3c4d278aedd88019236c1425e');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '2f5735d125b4537076893a4b4a37a188');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '374409ab56141b311ccb0f1847dd724a');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '43563b039d30b990f87af37783115ff4');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '5ce2349dc38a84cfbf0f5b260b41a2b6');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '6f8aff1f2c458e5add9adb6d284fb451');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '7c411c7d41034d6708103c8e0da19ced');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '8c0eb60ccef367ce7048e5d486aaa3a9');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '8fb8756a4587cc4c76401a63ea194568');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', '97a5eac3bfeeabe4013d828b919786f7');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'a2ccfe694cd91cf159ad35626e4ea202');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'b1df787d8af5b728181a4b9acf38da93');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'b29de942eeabc9419185951f57be11f3');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'bdd70f2c1ee068c13bd3288eff07c8e2');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'c212381ae7a2333416a18e486f044777');
INSERT INTO `agi_sys_role_menu` VALUES ('d0d0cab7c147d865d35e70fc62f2f19e', 'f1ad3c056ac91fa5292a99f223155afc');

-- ----------------------------
-- Table structure for agi_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_user`;
CREATE TABLE `agi_sys_user`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `dept_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0锁定 1有效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_user
-- ----------------------------
INSERT INTO `agi_sys_user` VALUES ('827450c4a39b3c4c14fdfb06f454bfb3', 'langchain', 't+F5wdaqrAdqZyEjXmCpFw==', '演示环境账号', '男', '185xxxxxxxxx', 'langchain@163.com', '14b300858a898c6dcfd3dc95dde6df81', NULL, 1, '2024-08-04 13:55:35');
INSERT INTO `agi_sys_user` VALUES ('91b4524a46a949601e7f3b004ed76034', 'administrator', 'iY/JVcnquYoJlKU9I9J+7Q==', '超级管理员', '男', '185xxxxxxxxx', 'langchain@163.com', NULL, NULL, 0, '2024-08-04 13:55:35');

-- ----------------------------
-- Table structure for agi_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `agi_sys_user_role`;
CREATE TABLE `agi_sys_user_role`  (
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agi_sys_user_role
-- ----------------------------
INSERT INTO `agi_sys_user_role` VALUES ('827450c4a39b3c4c14fdfb06f454bfb3', 'd0d0cab7c147d865d35e70fc62f2f19e');
INSERT INTO `agi_sys_user_role` VALUES ('91b4524a46a949601e7f3b004ed76034', '2827e950043adf67b7fe10306d3e94e4');

SET FOREIGN_KEY_CHECKS = 1;
