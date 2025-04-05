package org.dromara.langchain.upms.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色表(Role)实体类
 *
 */
@Data
@TableName(value = "agi_sys_role", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysRole implements Serializable {
	private static final long serialVersionUID = 547891924677981054L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色别名
	 */
	private String code;

	/**
	 * 描述
	 */
	private String des;
}
