package org.dromara.langchain.upms.service;

import java.util.List;

import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.AgiSysUserRole;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户角色关联表(UserRole)表服务接口
 *
 * 
 * 
 */
public interface AgiSysUserRoleService extends IService<AgiSysUserRole> {

	/**
	 * 根据RoleID查询User集合
	 */
	List<AgiSysUser> getUserListByRoleId(String roleId);

	/**
	 * 根据UserID查询Role集合
	 */
	List<AgiSysRole> getRoleListByUserId(String userId);

	/**
	 * 根据用户ID删除该用户的角色关联信息
	 */
	void deleteUserRolesByUserId(String userId);

	/**
	 * 根据角色ID删除该用户的角色关联信息
	 */
	void deleteUserRolesByRoleId(String roleId);
}
