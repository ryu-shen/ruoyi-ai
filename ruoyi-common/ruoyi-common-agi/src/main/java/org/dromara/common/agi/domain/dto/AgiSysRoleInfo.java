package org.dromara.common.agi.domain.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AgiSysRoleInfo implements Serializable {

	private static final long serialVersionUID = 547891924677981054L;

	/**
	 * 主键
	 */
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
