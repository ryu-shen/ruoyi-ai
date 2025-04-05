package org.dromara.langchain.upms.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.dromara.langchain.upms.domain.AgiSysDept;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;

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
public class AgiUserInfo extends AgiSysUser implements Serializable {
	private static final long serialVersionUID = 547891924677981054L;

	/**
	 * 用户所属部门
	 */
	private AgiSysDept dept;

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
	private List<AgiSysRole> roles;

	/**
	 * 用户权限标识
	 */
	private Set<String> perms;
}
