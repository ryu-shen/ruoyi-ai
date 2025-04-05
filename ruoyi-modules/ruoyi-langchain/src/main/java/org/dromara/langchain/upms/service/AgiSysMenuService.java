package org.dromara.langchain.upms.service;

import java.util.List;

import org.dromara.langchain.upms.domain.AgiSysMenu;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.dto.AgiMenuTree;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 菜单表(Menu)表服务接口
 *
 * 
 * 
 */
public interface AgiSysMenuService extends IService<AgiSysMenu> {

	/**
	 * 构建菜单Tree树
	 */
	List<AgiMenuTree<AgiSysMenu>> tree(AgiSysMenu sysMenu);

	/**
	 * 构建左侧权限菜单
	 */
	List<AgiMenuTree<AgiSysMenu>> build(String userId);

	/**
	 * 根据用户ID查询权限信息
	 */
	List<AgiSysMenu> getUserMenuList(List<AgiSysRole> sysRoleList);

	/**
	 * 条件查询
	 */
	List<AgiSysMenu> list(AgiSysMenu sysMenu);

	/**
	 * 新增
	 */
	void add(AgiSysMenu sysMenu);

	/**
	 * 修改
	 */
	void update(AgiSysMenu sysMenu);

	/**
	 * 删除
	 */
	void delete(String id);

}
