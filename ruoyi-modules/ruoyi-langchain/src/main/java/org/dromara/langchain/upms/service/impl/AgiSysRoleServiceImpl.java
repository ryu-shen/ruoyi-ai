package org.dromara.langchain.upms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysRoleMenu;
import org.dromara.langchain.upms.domain.dto.AgiSysRoleDto;
import org.dromara.langchain.upms.mapper.AgiSysRoleMapper;
import org.dromara.langchain.upms.mapper.AgiSysRoleMenuMapper;
import org.dromara.langchain.upms.mapper.AgiSysUserRoleMapper;
import org.dromara.langchain.upms.service.AgiSysRoleMenuService;
import org.dromara.langchain.upms.service.AgiSysRoleService;
import org.dromara.langchain.upms.service.AgiSysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;

/**
 * 角色表(Role)表服务实现类
 * 
 */
@Service
@RequiredArgsConstructor
public class AgiSysRoleServiceImpl extends ServiceImpl<AgiSysRoleMapper, AgiSysRole> implements AgiSysRoleService {

	private final AgiSysRoleMenuService sysRoleMenuService;
	private final AgiSysUserRoleService sysUserRoleService;
	private final AgiSysUserRoleMapper sysUserRoleMapper;
	private final AgiSysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public IPage<AgiSysRole> page(AgiSysRole role, QueryPage queryPage) {
		return baseMapper.selectPage(MybatisUtil.wrap(role, queryPage), Wrappers.<AgiSysRole>lambdaQuery()
				.like(StringUtils.isNotEmpty(role.getName()), AgiSysRole::getName, role.getName()));
	}

	@Override
	public List<AgiSysRole> findRolesByUserId(String id) {
		return sysUserRoleMapper.getRoleListByUserId(id);
	}

	private List<String> getMenuIdsByRoleId(String roleId) {
		List<AgiSysRoleMenu> list = sysRoleMenuMapper
				.selectList(new LambdaQueryWrapper<AgiSysRoleMenu>().eq(AgiSysRoleMenu::getRoleId, roleId));
		return list.stream().map(AgiSysRoleMenu::getMenuId).collect(Collectors.toList());
	}

	@Override
	public AgiSysRoleDto findById(String roleId) {
		AgiSysRole role = this.getById(roleId);
		AgiSysRoleDto sysRole = BeanUtil.copyProperties(role, AgiSysRoleDto.class);
		sysRole.setMenuIds(getMenuIdsByRoleId(roleId));
		return sysRole;
	}

	public boolean checkCode(AgiSysRoleDto data) {
		if (AgiLoginHelperUtil.ADMINISTRATOR.equals(data.getCode())
				|| AgiLoginHelperUtil.DEFAULT_ROLE.equals(data.getCode())) {
			return false;
		}
		LambdaQueryWrapper<AgiSysRole> queryWrapper = new LambdaQueryWrapper<AgiSysRole>().eq(AgiSysRole::getCode,
				data.getCode());
		if (data.getId() != null) {
			queryWrapper.ne(AgiSysRole::getId, data.getId());
		}
		return baseMapper.selectList(queryWrapper).size() <= 0;
	}

	@Override
	public void add(AgiSysRoleDto sysRole) {
		if (!checkCode(sysRole)) {
			throw new AgiServiceException("该角色已存在");
		}
		this.save(sysRole);
		addMenus(sysRole);
	}

	@Override
	public void update(AgiSysRoleDto sysRole) {
		checkCode(sysRole);
		baseMapper.updateById(sysRole);
		addMenus(sysRole);
	}

	private void addMenus(AgiSysRoleDto sysRole) {
		List<String> menuIds = sysRole.getMenuIds();
		String id = sysRole.getId();
		if (menuIds != null) {
			// 先删除原有的关联
			sysRoleMenuService.deleteRoleMenusByRoleId(id);

			// 再新增关联
			List<AgiSysRoleMenu> sysRoleMenuList = new ArrayList<>();
			menuIds.forEach(menuId -> sysRoleMenuList.add(new AgiSysRoleMenu().setMenuId(menuId).setRoleId(id)));
			sysRoleMenuService.saveBatch(sysRoleMenuList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String id) {
		AgiSysRole sysRole = this.getById(id);
		if (!checkCode(BeanUtil.copyProperties(sysRole, AgiSysRoleDto.class))) {
			throw new AgiServiceException("该角色不可删除");
		}
		baseMapper.deleteById(id);
		sysRoleMenuService.deleteRoleMenusByRoleId(id);
		sysUserRoleService.deleteUserRolesByRoleId(id);
	}
}
