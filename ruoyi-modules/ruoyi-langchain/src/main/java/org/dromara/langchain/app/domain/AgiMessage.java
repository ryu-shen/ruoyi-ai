package org.dromara.langchain.app.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_message", autoResultMap = true)
@Accessors(chain = true)
public class AgiMessage extends BaseEntity {

	private static final long serialVersionUID = -19545329638997333L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 消息ID
	 */
	private String chatId;

	/**
	 * 会话ID
	 */
	private String conversationId;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 请求IP
	 */
	private String ip;

	private Integer tokens;
	private Integer promptTokens;

	/**
	 * 角色，user、assistant
	 */
	private String role;

	/**
	 * 消息内容
	 */
	private String model;

	/**
	 * 消息内容
	 */
	private String message;

}
