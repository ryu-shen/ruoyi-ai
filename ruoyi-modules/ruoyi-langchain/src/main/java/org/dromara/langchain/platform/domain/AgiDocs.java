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
@TableName(value = "agi_docs", autoResultMap = true)
@Accessors(chain = true)
public class AgiDocs extends BaseEntity {
	private static final long serialVersionUID = 548724967827903685L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 知识库ID
	 */
	private String knowledgeId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 来源
	 */
	private String origin;
	private String url;

	/**
	 * 文件大小
	 */
	private Long size;

	/**
	 * 切片数量
	 */
	private Integer sliceNum;

	/**
	 * 切片状态
	 */
	private Boolean sliceStatus;

	/**
	 * 文档内容
	 */
	private String content;

}
