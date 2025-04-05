package org.dromara.langchain.upms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.config.AuthProps;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.upms.domain.AgiSysDept;
import org.dromara.langchain.upms.domain.AgiSysMenu;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.AgiSysUserRole;
import org.dromara.langchain.upms.domain.constant.UpmsConst;
import org.dromara.langchain.upms.domain.dto.AgiUserInfo;
import org.dromara.langchain.upms.mapper.AgiSysUserMapper;
import org.dromara.langchain.upms.service.AgiSysDeptService;
import org.dromara.langchain.upms.service.AgiSysMenuService;
import org.dromara.langchain.upms.service.AgiSysRoleService;
import org.dromara.langchain.upms.service.AgiSysUserRoleService;
import org.dromara.langchain.upms.service.AgiSysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

/**
 * 用户表(User)表服务实现类
 *
 */
@Service
@RequiredArgsConstructor
public class AgiSysUserServiceImpl extends ServiceImpl<AgiSysUserMapper, AgiSysUser> implements AgiSysUserService {

	private final AgiSysRoleService sysRoleService;
	private final AgiSysMenuService sysMenuService;
	private final AgiSysDeptService sysDeptService;
	private final AgiSysUserRoleService sysUserRoleService;
	private final AuthProps authProps;

	@Override
	public AgiSysUser findByName(String username) {
		return baseMapper.selectOne(new LambdaQueryWrapper<AgiSysUser>().eq(AgiSysUser::getUsername, username));
	}

	@Override
	public AgiUserInfo findById(String userId) {
		AgiSysUser sysUser = baseMapper.selectById(userId);
		if (sysUser == null) {
			return null;
		}
		AgiUserInfo dto = BeanUtil.copyProperties(sysUser, AgiUserInfo.class);
		dto.setPassword(null);
		List<String> roleIds = sysUserRoleService.getRoleListByUserId(userId).stream().map(AgiSysRole::getId)
				.collect(Collectors.toList());
		dto.setRoleIds(roleIds);
		return dto;
	}

	@Override
//    @Cacheable(value = CacheConst.USER_DETAIL_KEY, key = "#username")
	public AgiUserInfo info(String username) {
		AgiSysUser user = this.findByName(username);
		AgiUserInfo userInfo = BeanUtil.copyProperties(user, AgiUserInfo.class);
		return this.build(userInfo);
	}

	/**
	 * 构建用户信息、角色信息、权限标识信息、部门信息
	 */
	private AgiUserInfo build(AgiUserInfo userInfo) {
		if (userInfo == null) {
			throw new AgiServiceException("没有查询用用户信息", 401);
		}
		// 获取用户角色列表
		List<AgiSysRole> sysRoleList = sysRoleService.findRolesByUserId(userInfo.getId());
		if (!sysRoleList.isEmpty()) {
			// 获取用户权限列表
			List<AgiSysMenu> menuList = new ArrayList<>();
			long isAdmin = sysRoleList.stream().filter(role -> AgiLoginHelperUtil.ADMINISTRATOR.equals(role.getCode()))
					.count();
			if (isAdmin > 0) {
				// 包含了超级管理员角色，拥有所有权限
				menuList = sysMenuService.list();
			} else {
				// 根据角色筛选权限
				menuList = sysMenuService.getUserMenuList(sysRoleList);
			}
			Set<String> perms = menuList.stream().map(AgiSysMenu::getPerms).filter(StringUtils::isNotEmpty)
					.collect(Collectors.toSet());

			List<String> roleIds = sysRoleList.stream().map(AgiSysRole::getId).toList();
			userInfo.setRoleIds(roleIds);

			// 获取用户部门信息
			AgiSysDept sysDept = sysDeptService.getById(userInfo.getDeptId());
			return userInfo.setRoles(sysRoleList).setPerms(perms).setDept(sysDept);
		}

		return userInfo;
	}

	@Override
	public List<AgiSysUser> list(AgiSysUser sysUser) {
		List<AgiSysUser> list = baseMapper.selectList(
				new LambdaQueryWrapper<AgiSysUser>().ne(AgiSysUser::getUsername, AgiLoginHelperUtil.ADMINISTRATOR).like(
						StringUtils.isNotEmpty(sysUser.getUsername()), AgiSysUser::getUsername, sysUser.getUsername()));
		list.forEach(i -> i.setPassword(null));
		return list;
	}

	@Override
	public IPage<AgiUserInfo> page(AgiUserInfo user, QueryPage queryPage) {
		return baseMapper.page(MybatisUtil.wrap(user, queryPage), user, AgiLoginHelperUtil.getUserId(),
				AgiLoginHelperUtil.ADMINISTRATOR);
	}

	@Override
	public boolean checkName(AgiUserInfo sysUser) {
		if (AgiLoginHelperUtil.ADMINISTRATOR.equals(sysUser.getUsername())) {
			return false;
		}
		LambdaQueryWrapper<AgiSysUser> queryWrapper = new LambdaQueryWrapper<AgiSysUser>().eq(AgiSysUser::getUsername,
				sysUser.getUsername());
		if (sysUser.getId() != null) {
			queryWrapper.ne(AgiSysUser::getId, sysUser.getId());
		}
		return baseMapper.selectList(queryWrapper).size() <= 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void add(AgiUserInfo user) {
		if (!checkName(user)) {
			throw new AgiServiceException("该用户名已存在，请重新输入！");
		}
		if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
			throw new AgiServiceException("用户名或密码为空，请重新输入！");
		}

		user.setCreateTime(new Date());

		// 设置角色
		if (user.getRoleIds() == null || user.getRoleIds().isEmpty()) {
			throw new AgiServiceException("用户角色不能为空");
		}
		baseMapper.insert(user);
		addRole(user);
	}

	private void addRole(AgiUserInfo user) {
		List<String> roleIds = user.getRoleIds();
		String userId = user.getId();
		if (roleIds != null) {
			// 删除之前用户与角色表之前的关联，并重新建立关联
			sysUserRoleService.deleteUserRolesByUserId(userId);

			// 新增用户角色关联
			List<AgiSysUserRole> list = new ArrayList<>();
			roleIds.forEach(roleId -> list.add(new AgiSysUserRole().setUserId(userId).setRoleId(roleId)));
			sysUserRoleService.saveBatch(list);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = UpmsConst.USER_DETAIL_KEY, key = "#user.username")
	public void update(AgiUserInfo user) {
		if (user.getUsername().equals(AgiLoginHelperUtil.ADMINISTRATOR)) {
			throw new AgiServiceException("超级管理员用户不可操作");
		}
		if (!checkName(user)) {
			throw new AgiServiceException("该用户名以存在，请重新输入");
		}
		user.setPassword(null);
		baseMapper.updateById(user);
		addRole(user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = UpmsConst.USER_DETAIL_KEY, key = "#username")
	public void delete(String id, String username) {
		if (username.equals(AgiLoginHelperUtil.ADMINISTRATOR)) {
			throw new AgiServiceException("超级管理员用户不可操作");
		}
		baseMapper.deleteById(id);
		sysUserRoleService.deleteUserRolesByUserId(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = UpmsConst.USER_DETAIL_KEY, key = "#username")
	public void reset(String id, String password, String username) {
		if (username.equals(AgiLoginHelperUtil.ADMINISTRATOR)) {
			throw new AgiServiceException("超级管理员用户不可操作");
		}
		AgiSysUser user = new AgiSysUser();
		user.setId(id);
		user.setPassword(AgiLoginHelperUtil.encode(authProps.getSaltKey(), password));
		baseMapper.updateById(user);
	}
}
