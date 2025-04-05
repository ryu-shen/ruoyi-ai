package org.dromara.langchain.upms.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.dromara.common.agi.domain.constant.TokenConst;
import org.dromara.common.agi.domain.dto.AgiAuthUserInfo;
import org.dromara.common.core.domain.Result;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.config.AuthProps;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.system.util.AgiSysLogUtil;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.constant.UpmsConst;
import org.dromara.langchain.upms.domain.dto.AgiTokenInfo;
import org.dromara.langchain.upms.domain.dto.AgiUserInfo;
import org.dromara.langchain.upms.service.AgiSysRoleService;
import org.dromara.langchain.upms.service.AgiSysUserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/upms/auth")
public class AgiAuthEndpoint {

	private final AgiSysUserService userService;
	private final AgiSysRoleService roleService;
	private final AuthProps authProps;

	@PostMapping("/login")
	public Result login(@RequestBody AgiUserInfo user) {
		if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
			throw new AgiServiceException("用户名或密码为空");
		}

		AgiUserInfo userInfo = userService.info(user.getUsername());
		if (userInfo == null) {
			throw new AgiServiceException("用户名或密码错误");
		}
		if (!AgiLoginHelperUtil.ADMINISTRATOR.equals(userInfo.getUsername()) && !userInfo.getStatus()) {
			throw new AgiServiceException("该用户已经禁用，请联系管理员");
		}

		String decryptPass = AgiLoginHelperUtil.decrypt(authProps.getSaltKey(), userInfo.getPassword());
		if (!decryptPass.equals(user.getPassword())) {
			throw new AgiServiceException("密码不正确");
		}

		StpUtil.login(userInfo.getId());
		SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
		// AgiUserInfo->AgiAuthUserInfo存入session中
		AgiAuthUserInfo agiAuthUserInfo = new AgiAuthUserInfo();
		BeanUtil.copyProperties(userInfo, agiAuthUserInfo, false);
		StpUtil.getSession().set(TokenConst.AUTH_USER_INFO_KEY, agiAuthUserInfo).set(TokenConst.AUTH_TOKEN_INFO_KEY,
				tokenInfo);

		AgiSysLogUtil.publish(1, "服务端登录", AgiLoginHelperUtil.getUsername());
		log.info("====> login success，token={}", tokenInfo.getTokenValue());
		return Result.ok(new AgiTokenInfo().setToken(tokenInfo.tokenValue).setExpiration(tokenInfo.tokenTimeout));
	}

	@DeleteMapping("/logout")
	public Result logout() {
		StpUtil.logout();
		return Result.ok();
	}

	@PostMapping("/register")
	public Result emailRegister(@RequestBody AgiSysUser data) {
		if (StrUtil.isBlank(data.getUsername()) || StrUtil.isBlank(data.getPassword())) {
			throw new AgiServiceException("用户名或密码为空");
		}

		// 校验用户名是否已存在
		List<AgiSysUser> list = userService
				.list(Wrappers.<AgiSysUser>lambdaQuery().eq(AgiSysUser::getUsername, data.getUsername()));
		if (!list.isEmpty()) {
			throw new AgiServiceException("该用户名已存在");
		}

		List<AgiSysRole> roles = roleService
				.list(Wrappers.<AgiSysRole>lambdaQuery().eq(AgiSysRole::getCode, AgiLoginHelperUtil.DEFAULT_ROLE));
		if (roles.isEmpty()) {
			throw new AgiServiceException("系统角色配置异常，请联系管理员");
		}

		AgiUserInfo user = (AgiUserInfo) new AgiUserInfo().setRoleIds(roles.stream().map(AgiSysRole::getId).toList())
				.setUsername(data.getUsername())
				.setPassword(AgiLoginHelperUtil.encode(authProps.getSaltKey(), data.getPassword()))
				.setRealName(data.getUsername()).setPhone(data.getPhone()).setStatus(true).setCreateTime(new Date());
		userService.add(user);
		AgiSysLogUtil.publish(1, "服务端注册", user.getUsername());
		return Result.ok();
	}

	@GetMapping("/info")
	public Result<AgiUserInfo> info() {
		AgiUserInfo userInfo = userService.info(AgiLoginHelperUtil.getUsername());
		userInfo.setPassword(null);
		return Result.ok(userInfo);
	}

	@DeleteMapping("/token/{token}")
	@SaCheckPermission("auth:delete")
	public Result tokenDel(@PathVariable String token) {
		StpUtil.kickoutByTokenValue(token);
		return Result.ok();
	}

	@GetMapping("/token/page")
	public Result tokenPage(QueryPage queryPage) {
		List<String> tokenKeys = StpUtil.searchTokenValue("", queryPage.getPage() - 1, queryPage.getLimit(), true);
		Map<String, Object> loginIdMap = RedisUtils.getClient().getBuckets()
				.get(ArrayUtil.toArray(tokenKeys, String.class));
		Collection<String> keys = RedisUtils.keys(UpmsConst.AUTH_SESSION_PREFIX + "*");

		List<Object> result = new ArrayList<>();
		Iterator<Map.Entry<String, Object>> iterator = loginIdMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String authorizationKey = entry.getKey();
			String loginId = (String) entry.getValue();

			Dict data = Dict.create();
			SaSession saSession = StpUtil.getSessionByLoginId(loginId);
			Map<String, Object> dataMap = saSession.getDataMap();
			AgiAuthUserInfo loginUser = (AgiAuthUserInfo) saSession.get(TokenConst.AUTH_USER_INFO_KEY);
			AgiUserInfo userInfo = new AgiUserInfo();
			BeanUtil.copyProperties(loginUser, userInfo, false);

			if (Objects.equals(AgiLoginHelperUtil.getUserId(), userInfo.getId())) {
				continue;
			}
			SaTokenInfo tokenInfo = (SaTokenInfo) dataMap.get(TokenConst.AUTH_TOKEN_INFO_KEY);
			if (tokenInfo == null) {
				continue;
			}
			data.set("token", tokenInfo.tokenValue);
			data.set("perms", userInfo.getPerms());
			data.set("roles", userInfo.getRoles());
			data.set("email", userInfo.getEmail());
			data.set("id", userInfo.getId());
			data.set("username", userInfo.getUsername());
			data.set("realName", userInfo.getRealName());

			long expiration = StpUtil.getTokenTimeout();
			Date targetDate = new Date(System.currentTimeMillis() + expiration);
			String formattedDate = DateUtil.format(targetDate, DatePattern.NORM_DATETIME_PATTERN);
			data.set("expiration", formattedDate);

			result.add(data);
		}

		IPage page = new Page(queryPage.getPage(), queryPage.getLimit());
		page.setRecords(result);
		page.setTotal(keys == null ? 0 : keys.size());
		return Result.ok(MybatisUtil.getData(page));
	}
}
