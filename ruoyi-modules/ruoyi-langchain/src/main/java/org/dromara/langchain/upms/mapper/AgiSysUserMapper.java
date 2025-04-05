package org.dromara.langchain.upms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.dto.AgiUserInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.lang.Dict;

/**
 * 用户表(User)表数据库访问层
 * 
 */
@Mapper
public interface AgiSysUserMapper extends BaseMapper<AgiSysUser> {

	@Select("""
			    SELECT
			        COALESCE(COUNT(*), 0) AS totalUser,
			        COALESCE(SUM( CASE WHEN YEAR ( create_time ) = YEAR ( CURDATE()) AND MONTH ( create_time ) = MONTH ( CURDATE()) THEN 1 ELSE 0 END ), 0) AS curUser
			    FROM
			        sys_user;
			""")
	Dict getCount();

	IPage<AgiUserInfo> page(IPage<AgiSysUser> page, AgiUserInfo user, String ignoreId, String ignoreName);
}
