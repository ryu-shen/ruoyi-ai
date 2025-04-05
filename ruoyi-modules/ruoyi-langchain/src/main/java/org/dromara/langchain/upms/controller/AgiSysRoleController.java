package org.dromara.langchain.upms.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.upms.domain.AgiSysRole;
import org.dromara.langchain.upms.domain.dto.AgiSysRoleDto;
import org.dromara.langchain.upms.service.AgiSysRoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;

/**
 * 角色表(Role)表控制层
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/role")
public class AgiSysRoleController {

	private final AgiSysRoleService sysRoleService;

	@GetMapping("/list")
	public Result<List<AgiSysRole>> list(AgiSysRole sysRole) {
		return Result.ok(sysRoleService
				.list(new LambdaQueryWrapper<AgiSysRole>().ne(AgiSysRole::getCode, AgiLoginHelperUtil.ADMINISTRATOR)));
	}

	@GetMapping("/page")
	public Result<Dict> page(AgiSysRole role, QueryPage queryPage) {
		return Result.ok(MybatisUtil.getData(sysRoleService.page(role, queryPage)));
	}

	@GetMapping("/{id}")
	public Result<AgiSysRoleDto> findById(@PathVariable String id) {
		return Result.ok(sysRoleService.findById(id));
	}

	@PostMapping
	@SaCheckPermission("upms:role:add")
	public Result add(@RequestBody AgiSysRoleDto sysRole) {
		sysRoleService.add(sysRole);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("upms:role:update")
	public Result update(@RequestBody AgiSysRoleDto sysRole) {
		sysRoleService.update(sysRole);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("upms:role:delete")
	public Result delete(@PathVariable String id) {
		sysRoleService.delete(id);
		return Result.ok();
	}
}
