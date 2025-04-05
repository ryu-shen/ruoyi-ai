package org.dromara.langchain.upms.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.config.AuthProps;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.upms.domain.AgiSysUser;
import org.dromara.langchain.upms.domain.dto.AgiUserInfo;
import org.dromara.langchain.upms.service.AgiSysUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;

/**
 * 用户表(User)表控制层
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/user")
public class AgiSysUserController {

	private final AgiSysUserService sysUserService;
	private final AuthProps authProps;

	@GetMapping("/checkName")
	public Result<Boolean> checkName(AgiUserInfo sysUser) {
		return Result.ok(sysUserService.checkName(sysUser));
	}

	@GetMapping("/list")
	public Result<List<AgiSysUser>> list(AgiSysUser sysUser) {
		return Result.ok(sysUserService.list(sysUser));
	}

	@GetMapping("/page")
	public Result<Dict> page(AgiUserInfo user, QueryPage queryPage) {
		return Result.ok(MybatisUtil.getData(sysUserService.page(user, queryPage)));
	}

	@GetMapping("/{id}")
	public Result<AgiUserInfo> findById(@PathVariable String id) {
		return Result.ok(sysUserService.findById(id));
	}

	@PostMapping
	@SaCheckPermission("upms:user:add")
	public Result<AgiSysUser> add(@RequestBody AgiUserInfo user) {
		user.setPassword(AgiLoginHelperUtil.encode(authProps.getSaltKey(), user.getPassword()));
		sysUserService.add(user);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("upms:user:update")
	public Result update(@RequestBody AgiUserInfo user) {
		sysUserService.update(user);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("upms:user:delete")
	public Result delete(@PathVariable String id) {
		AgiSysUser user = sysUserService.getById(id);
		if (user != null) {
			sysUserService.delete(id, user.getUsername());
		}
		return Result.ok();
	}

	@PutMapping("/resetPass")
	@SaCheckPermission("upms:user:reset")
	public Result resetPass(@RequestBody AgiUserInfo data) {
		AgiSysUser user = sysUserService.getById(data.getId());
		if (user != null) {
			sysUserService.reset(data.getId(), data.getPassword(), user.getUsername());
		}
		return Result.ok();
	}

	@PutMapping("/updatePass")
	@SaCheckPermission("upms:user:updatePass")
	public Result updatePass(@RequestBody AgiUserInfo data) {
		AgiSysUser user = sysUserService.getById(data.getId());
		if (user == null
				|| !AgiLoginHelperUtil.decrypt(authProps.getSaltKey(), user.getPassword()).equals(data.getPassword())) {
			throw new ServiceException("Old password entered incorrectly, please re-enter",
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		user.setPassword(AgiLoginHelperUtil.encode(authProps.getSaltKey(), data.getPassword()));
		return Result.ok();
	}
}
