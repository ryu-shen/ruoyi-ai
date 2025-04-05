package org.dromara.langchain.system.controller;

import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.system.domain.AgiSysLog;
import org.dromara.langchain.system.service.AgiSysLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;

/**
 * 系统日志表(Log)表控制层
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/log")
public class AgiSysLogController {

	private final AgiSysLogService sysLogService;

	@GetMapping("/page")
	public Result<Dict> list(AgiSysLog sysLog, QueryPage queryPage) {
		return Result.ok(MybatisUtil.getData(sysLogService.list(sysLog, queryPage)));
	}

	@GetMapping("/{id}")
	public Result<AgiSysLog> findById(@PathVariable String id) {
		return Result.ok(sysLogService.getById(id));
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("system:log:delete")
	public Result delete(@PathVariable String id) {
		sysLogService.delete(id);
		return Result.ok();
	}
}
