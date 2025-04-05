package org.dromara.langchain.upms.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色资源关联表(RoleMenu)实体类
 *
 */
@Data
@TableName(value = "agi_sys_role_menu", autoResultMap = true)
@Accessors(chain = true)
public class AgiSysRoleMenu implements Serializable {
	private static final long serialVersionUID = 854296054659457976L;

	/**
	 * 角色ID
	 */
	private String roleId;

	/**
	 * 菜单ID
	 */
	private String menuId;
}
