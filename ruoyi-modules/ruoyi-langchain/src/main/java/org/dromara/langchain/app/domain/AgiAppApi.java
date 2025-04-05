package org.dromara.langchain.app.domain;

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
@TableName(value = "agi_app_api", autoResultMap = true)
@Accessors(chain = true)
public class AgiAppApi extends BaseEntity {
	private static final long serialVersionUID = -94917153262781949L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	private String appId;
	private String apiKey;
	private String channel;

	@TableField(exist = false)
	private AgiApp app;
}
