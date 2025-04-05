package org.dromara.langchain.upms.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.langchain.upms.domain.AgiSysDept;
import org.dromara.langchain.upms.service.AgiSysDeptService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;

/**
 * 部门表(Dept)表控制层
 *
 * 
 * 
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/upms/dept")
public class AgiSysDeptController {

	private final AgiSysDeptService sysDeptService;

	@GetMapping("/list")
	public Result<List<AgiSysDept>> list(AgiSysDept sysDept) {
		return Result.ok(sysDeptService.list(sysDept));
	}

	@GetMapping("/tree")
	public Result<List<Tree<Object>>> tree(AgiSysDept sysDept) {
		return Result.ok(sysDeptService.tree(sysDept));
	}

	@GetMapping("/{id}")
	public Result<AgiSysDept> findById(@PathVariable String id) {
		return Result.ok(sysDeptService.getById(id));
	}

	@PostMapping
	@SaCheckPermission("upms:dept:add")
	public Result add(@RequestBody AgiSysDept sysDept) {
		sysDept.setParentId(sysDept.getParentId());
		sysDeptService.save(sysDept);
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("upms:dept:update")
	public Result update(@RequestBody AgiSysDept sysDept) {
		sysDept.setParentId(sysDept.getParentId());
		sysDeptService.updateById(sysDept);
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("upms:dept:delete")
	public Result delete(@PathVariable String id) {
		sysDeptService.delete(id);
		return Result.ok();
	}
}
