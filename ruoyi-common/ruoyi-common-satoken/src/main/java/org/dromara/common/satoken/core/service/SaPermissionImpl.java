package org.dromara.common.satoken.core.service;

import java.util.ArrayList;
import java.util.List;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.UserType;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.common.satoken.utils.LoginHelper;

import cn.dev33.satoken.stp.StpInterface;

/**
 * sa-token 权限管理实现类
 *
 * @author Lion Li
 */
public class SaPermissionImpl implements StpInterface {

	/**
	 * 获取菜单权限列表
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		LoginUser loginUser = LoginHelper.getLoginUser();
		// Ryu修改
		if (loginUser == null) {
			return AgiLoginHelperUtil.getPermissionNames();
		}

		UserType userType = UserType.getUserType(loginUser.getUserType());
		if (userType == UserType.SYS_USER) {
			return new ArrayList<>(loginUser.getMenuPermission());
		} else if (userType == UserType.APP_USER) {
			// 其他端 自行根据业务编写
		}
		return new ArrayList<>();
	}

	/**
	 * 获取角色权限列表
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		LoginUser loginUser = LoginHelper.getLoginUser();
		// Ryu修改
		if (loginUser == null) {
			return AgiLoginHelperUtil.getRoleNames();
		}

		UserType userType = UserType.getUserType(loginUser.getUserType());
		if (userType == UserType.SYS_USER) {
			return new ArrayList<>(loginUser.getRolePermission());
		} else if (userType == UserType.APP_USER) {
			// 其他端 自行根据业务编写
		}
		return new ArrayList<>();
	}
}
