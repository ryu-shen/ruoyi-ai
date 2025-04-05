package org.dromara.langchain.upms.service.impl;

import java.util.List;

import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.AgiSysUserRole;
import org.dromara.langchain.upms.mapper.AgiSysUserRoleMapper;
import org.dromara.langchain.upms.service.AgiSysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 用户角色关联表(UserRole)表服务实现类
 *
 * 
 * 
 */
@Service
public class AgiSysUserRoleServiceImpl extends ServiceImpl<AgiSysUserRoleMapper, AgiSysUserRole>
		implements AgiSysUserRoleService {

	@Override
	public List<AgiSysUser> getUserListByRoleId(String roleId) {
		return baseMapper.getUserListByRoleId(roleId);
	}

	@Override
	public List<AgiSysRole> getRoleListByUserId(String userId) {
		return baseMapper.getRoleListByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserRolesByUserId(String userId) {
		baseMapper.delete(new LambdaQueryWrapper<AgiSysUserRole>().eq(AgiSysUserRole::getUserId, userId));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserRolesByRoleId(String roleId) {
		baseMapper.delete(new LambdaQueryWrapper<AgiSysUserRole>().eq(AgiSysUserRole::getRoleId, roleId));
	}
}
