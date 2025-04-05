package org.dromara.langchain.platform.domain;

import java.util.ArrayList;
import java.util.List;

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
@TableName(value = "agi_knowledge", autoResultMap = true)
@Accessors(chain = true)
public class AgiKnowledge extends BaseEntity {
	private static final long serialVersionUID = 548724967827903685L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	private String embedStoreId;
	private String embedModelId;

	/**
	 * 知识库名称
	 */
	private String name;

	/**
	 * 封面
	 */
	private String cover;

	/**
	 * 描述
	 */
	private String des;

	@TableField(exist = false)
	private Integer docsNum = 0;
	@TableField(exist = false)
	private Long totalSize = 0L;
	@TableField(exist = false)
	private List<AgiDocs> docs = new ArrayList<>();

	@TableField(exist = false)
	private AgiEmbedStore embedStore;
	@TableField(exist = false)
	private AgiModel embedModel;
}
