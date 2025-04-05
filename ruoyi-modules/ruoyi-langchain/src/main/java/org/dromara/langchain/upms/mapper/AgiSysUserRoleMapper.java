package org.dromara.langchain.upms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.AgiSysUserRole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户角色关联表(UserRole)表数据库访问层
 *
 * 
 * 
 */
@Mapper
public interface AgiSysUserRoleMapper extends BaseMapper<AgiSysUserRole> {

	/**
	 * 根据RoleID查询User集合
	 */
	List<AgiSysUser> getUserListByRoleId(String roleId);

	/**
	 * 根据UserID查询Role集合
	 */
	List<AgiSysRole> getRoleListByUserId(String userId);
}
