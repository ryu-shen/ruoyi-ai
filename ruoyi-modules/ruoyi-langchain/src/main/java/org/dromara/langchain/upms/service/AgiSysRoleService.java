package org.dromara.langchain.upms.service;

import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.dto.AgiSysRoleDto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色表(Role)表服务接口
 *
 * 
 * 
 */
public interface AgiSysRoleService extends IService<AgiSysRole> {

	/**
	 * 分页、条件查询
	 */
	IPage<AgiSysRole> page(AgiSysRole role, QueryPage queryPage);

	/**
	 * 根据用户ID查询其关联的所有角色
	 */
	List<AgiSysRole> findRolesByUserId(String id);

	/**
	 * 根据ID查询
	 */
	AgiSysRoleDto findById(String roleId);

	/**
	 * 新增角色
	 */
	void add(AgiSysRoleDto sysRole);

	/**
	 * 修改角色
	 */
	void update(AgiSysRoleDto sysRole);

	/**
	 * 删除
	 */
	void delete(String id);
}
