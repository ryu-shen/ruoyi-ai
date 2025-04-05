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
@TableName(value = "agi_embed_store", autoResultMap = true)
@Accessors(chain = true)
public class AgiEmbedStore extends BaseEntity {
	private static final long serialVersionUID = 548724967827903685L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	private String name;

	private String provider;
	private String host;
	private Integer port;
	private String username;
	private String password;
	private String databaseName;
	private String tableName;
	private Integer dimension;
}
