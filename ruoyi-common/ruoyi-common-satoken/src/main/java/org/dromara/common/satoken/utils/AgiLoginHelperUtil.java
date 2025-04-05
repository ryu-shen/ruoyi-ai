package org.dromara.common.satoken.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dromara.common.agi.domain.constant.TokenConst;
import org.dromara.common.agi.domain.dto.AgiAuthUserInfo;
import org.dromara.common.agi.domain.dto.AgiSysRoleInfo;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.ServletUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 权限相关方法
 */
public class AgiLoginHelperUtil {

	/**
	 * 系统预制固定超级管理员角色别名
	 * 
	 * 作用：提供一个角色摆脱权限体系的控制，允许词角色访问所有菜单权限
	 * 
	 * 使用：所有涉及根据角色查询的地方都排除对此角色的限制
	 */
	public static final String ADMINISTRATOR = "administrator";
	public static final String DEMO_ROLE = "demo_env";
	public static final String DEFAULT_ROLE = "default_env";

	/**
	 * 获取Request对象
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
				.getRequest();
	}

	/**
	 * 获取Response对象
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
				.getResponse();
	}

	/**
	 * 截取前端Token字符串中不包含`Bearer`的部分
	 */
	public static String getToken(String token) {
		if (token != null && token.toLowerCase().startsWith("bearer")) {
			// 去掉bearer字段
			return token.replace("bearer", "").trim();
		}
		return token;
	}

	public static String getAuthorizationToken() {
		HttpServletRequest httpServletRequest = ServletUtils.getRequest();
		if (httpServletRequest == null) {
			return null;
		}
		String token = httpServletRequest.getHeader("Authorization");
		if (token != null && token.toLowerCase().startsWith("bearer")) {
			// 去掉bearer字段
			return token.toLowerCase().replace("bearer", "").trim();
		}
		return null;
	}

	/**
	 * 获取用户数据
	 */
	public static AgiAuthUserInfo getUserInfo() {
		try {
			return (AgiAuthUserInfo) StpUtil.getSession().get(TokenConst.AUTH_USER_INFO_KEY);
		} catch (Exception e) {
			throw new ServiceException("登录已失效，请重新登陆", 403);
		}
	}

	public static boolean isDemoEnv() {
		try {

			AgiAuthUserInfo info = (AgiAuthUserInfo) StpUtil.getSession().get(TokenConst.AUTH_USER_INFO_KEY);
			List<AgiSysRoleInfo> roles = info.getRoles();
			if (roles != null && !roles.isEmpty()) {
				List<AgiSysRoleInfo> list = roles.stream().filter(i -> i.getCode().equals(DEMO_ROLE)).toList();
				return !list.isEmpty();
			}
			return true;
		} catch (Exception ignored) {
			return true;
		}
	}

	/**
	 * 获取用户名
	 */
	public static String getUsername() {
		AgiAuthUserInfo userInfo = getUserInfo();
		if (userInfo == null) {
			return null;
		}
		return userInfo.getUsername();
	}

	/**
	 * 获取登录用户ID
	 */
	public static String getUserId() {
		AgiAuthUserInfo userInfo = getUserInfo();
		if (userInfo == null) {
			return null;
		}
		return userInfo.getId();
	}

	/**
	 * 获取用户角色Id集合
	 */
	public static List<String> getRoleIds() {
		AgiAuthUserInfo userInfo = getUserInfo();
		if (userInfo == null || userInfo.getRoleIds() == null) {
			return new ArrayList<>();
		}
		return userInfo.getRoleIds();
	}

	/**
	 * 获取用户角色Alias集合
	 */
	public static List<String> getRoleNames() {
		AgiAuthUserInfo userInfo = getUserInfo();
		if (userInfo == null || userInfo.getRoles() == null) {
			return new ArrayList<>();
		}
		return userInfo.getRoles().stream().map(AgiSysRoleInfo::getCode).toList();
	}

	/**
	 * 获取权限集合
	 */
	public static List<String> getPermissionNames() {
		AgiAuthUserInfo userInfo = getUserInfo();
		if (userInfo == null || userInfo.getPerms() == null) {
			return new ArrayList<>();
		}
		return userInfo.getPerms().stream().toList();
	}

	/**
	 * 密码加密
	 */
	public static String encode(String salt, String password) {
		return SaSecureUtil.aesEncrypt(salt, password);
	}

	/**
	 * 密码解密
	 */
	public static String decrypt(String salt, String password) {
		return SaSecureUtil.aesDecrypt(salt, password);
	}

	public static void main(String[] args) {
		String password = "administrator";
		String enPassword = encode("langchain-salt", password);
		System.out.println();
	}

}
