package org.dromara.common.agi.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 自定义Oauth2 授权成功后存储的用户数据
 *
 * 
 * 
 */
@Data
@Accessors(chain = true)
public class AgiAuthUserInfo extends AgiSysUserInfo implements Serializable {
	private static final long serialVersionUID = 547891924677981054L;

	/**
	 * 用户所属部门
	 */
	private AgiSysDeptInfo dept;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 角色ID列表
	 */
	private List<String> roleIds;

	/**
	 * 用户角色列表
	 */
	private List<AgiSysRoleInfo> roles;

	/**
	 * 用户权限标识
	 */
	private Set<String> perms;
}
