package org.dromara.langchain.upms.service;

import org.dromara.langchain.upms.domain.AgiSysRoleMenu;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色资源关联表(RoleMenu)表服务接口
 *
 * 
 * 
 */
public interface AgiSysRoleMenuService extends IService<AgiSysRoleMenu> {

	/**
	 * 根据角色ID删除该角色的权限关联信息
	 */
	void deleteRoleMenusByRoleId(String roleId);

	/**
	 * 根据权限ID删除角色权限关联信息
	 */
	void deleteRoleMenusByMenuId(String menuId);
}
