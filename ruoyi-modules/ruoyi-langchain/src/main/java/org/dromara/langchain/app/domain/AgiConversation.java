package org.dromara.langchain.app.domain;

import java.util.Date;

import org.dromara.common.mybatis.core.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_conversation", autoResultMap = true)
@Accessors(chain = true)
public class AgiConversation extends BaseEntity {

	private static final long serialVersionUID = -19545329638997333L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 提示词ID
	 */
	private String promptId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 会话标题
	 */
	private String title;

	/**
	 * 用户名
	 */
	@TableField(exist = false)
	private String username;

	/**
	 * 对话次数
	 */
	@TableField(exist = false)
	private Integer chatTotal;
	/**
	 * Token消耗量
	 */
	@TableField(exist = false)
	private Integer tokenUsed;
	/**
	 * 最后一次对话时间
	 */
	@TableField(exist = false)
	private Date endTime;
}
