package org.dromara.langchain.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.JdbcType;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.domain.AgiModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_app", autoResultMap = true)
@Accessors(chain = true)
public class AgiApp extends BaseEntity {

	private static final long serialVersionUID = -94917153262781949L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	private String modelId;

	@TableField(typeHandler = FastjsonTypeHandler.class, jdbcType = JdbcType.VARCHAR)
	private List<String> knowledgeIds;

	/**
	 * 名称
	 */
	private String name;
	private String cover;

	/**
	 * Prompt
	 */
	private String prompt;

	/**
	 * 应用描述
	 */
	private String des;

	/**
	 * 创建时间
	 */
	private Date saveTime;

	@TableField(exist = false)
	private AgiModel model;
	@TableField(exist = false)
	private List<AgiKnowledge> knowledges = new ArrayList<>();
}
