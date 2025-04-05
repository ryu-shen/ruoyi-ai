package org.dromara.langchain.upms.service.impl;

import org.dromara.langchain.upms.domain.AgiSysRoleMenu;
import org.dromara.langchain.upms.mapper.AgiSysRoleMenuMapper;
import org.dromara.langchain.upms.service.AgiSysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 角色资源关联表(RoleMenu)表服务实现类
 *
 */
@Service
public class AgiSysRoleMenuServiceImpl extends ServiceImpl<AgiSysRoleMenuMapper, AgiSysRoleMenu>
		implements AgiSysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleMenusByRoleId(String roleId) {
		baseMapper.delete(new LambdaQueryWrapper<AgiSysRoleMenu>().eq(AgiSysRoleMenu::getRoleId, roleId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRoleMenusByMenuId(String menuId) {
		baseMapper.delete(new LambdaQueryWrapper<AgiSysRoleMenu>().eq(AgiSysRoleMenu::getMenuId, menuId));
	}
}
