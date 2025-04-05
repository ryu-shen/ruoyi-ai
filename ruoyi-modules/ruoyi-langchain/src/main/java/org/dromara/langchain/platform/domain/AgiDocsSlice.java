package org.dromara.langchain.platform.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "agi_docs_slice", autoResultMap = true)
@Accessors(chain = true)
public class AgiDocsSlice extends BaseEntity {
	private static final long serialVersionUID = -3093489071059867065L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 向量库的ID
	 */
	private String vectorId;

	/**
	 * 文档ID
	 */
	private String docsId;

	/**
	 * 知识库ID
	 */
	private String knowledgeId;

	/**
	 * 文档名称
	 */
	private String name;

	/**
	 * 切片内容
	 */
	private String content;

	/**
	 * 字符数量
	 */
	private Integer wordNum;

	/**
	 * 是否Embedding
	 */
	private Boolean status = false;

}
