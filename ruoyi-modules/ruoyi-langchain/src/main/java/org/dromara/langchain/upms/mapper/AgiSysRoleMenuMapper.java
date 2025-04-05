package org.dromara.langchain.upms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.langchain.upms.domain.AgiSysRoleMenu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 角色资源关联表(RoleMenu)表数据库访问层
 *
 * 
 * 
 */
@Mapper
public interface AgiSysRoleMenuMapper extends BaseMapper<AgiSysRoleMenu> {

}
