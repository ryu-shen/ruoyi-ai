package org.dromara.langchain.upms.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.upms.domain.AgiSysMenu;
import org.dromara.langchain.upms.domain.dto.AgiMenuTree;
import org.dromara.langchain.upms.service.AgiSysMenuService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;

/**
 * 菜单表(Menu)表控制层
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/menu")
public class AgiSysMenuController {

	private final AgiSysMenuService sysMenuService;

	@GetMapping("/tree")
	public Result<List<AgiMenuTree<AgiSysMenu>>> tree(AgiSysMenu sysMenu) {
		return Result.ok(sysMenuService.tree(sysMenu));
	}

	@GetMapping("/build")
	public Result<List<AgiMenuTree<AgiSysMenu>>> build() {
		return Result.ok(sysMenuService.build(AgiLoginHelperUtil.getUserId()));
	}

	@GetMapping("/list")
	public Result<List<AgiSysMenu>> list(AgiSysMenu sysMenu) {
		return Result.ok(sysMenuService.list(sysMenu));
	}

	@GetMapping("/{id}")
	public Result<AgiSysMenu> findById(@PathVariable String id) {
		return Result.ok(sysMenuService.getById(id));
	}

	@PostMapping
	@SaCheckPermission("upms:menu:add")
	public Result add(@RequestBody AgiSysMenu sysMenu) {
		sysMenuService.add(sysMenu);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("upms:menu:update")
	public Result update(@RequestBody AgiSysMenu sysMenu) {
		sysMenuService.update(sysMenu);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("upms:menu:delete")
	public Result delete(@PathVariable String id) {
		sysMenuService.delete(id);
		return Result.ok();
	}
}
