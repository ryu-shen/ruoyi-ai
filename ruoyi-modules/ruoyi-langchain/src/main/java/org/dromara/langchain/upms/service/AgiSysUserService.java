package org.dromara.langchain.upms.service;

import java.util.List;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.dto.AgiUserInfo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表(User)表服务接口
 *
 * 
 * 
 */
public interface AgiSysUserService extends IService<AgiSysUser> {

	/**
	 * 根据用户名查询
	 */
	AgiSysUser findByName(String username);

	/**
	 * 根据ID查询
	 */
	AgiUserInfo findById(String userId);

	/**
	 * 查询用户数据
	 */
	AgiUserInfo info(String username);

	/**
	 * 条件查询
	 */
	List<AgiSysUser> list(AgiSysUser sysUser);

	/**
	 * 分页、条件查询
	 */
	IPage<AgiUserInfo> page(AgiUserInfo user, QueryPage queryPage);

	/**
	 * 校验用户名是否存在
	 */
	boolean checkName(AgiUserInfo sysUser);

	void add(AgiUserInfo user);

	void update(AgiUserInfo user);

	void delete(String id, String username);

	void reset(String id, String password, String username);
}
