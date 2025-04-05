package org.dromara.langchain.system.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.system.oss.domain.bo.SysOssConfigBo;
import org.dromara.system.oss.domain.vo.SysOssConfigVo;
import org.dromara.system.oss.service.ISysOssConfigService;
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
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;

@RequestMapping("/system/oss/config")
@RestController
@AllArgsConstructor
public class AgiSysOssConfigController {

	private final ISysOssConfigService ossConfigService;

	@GetMapping("/list")
	public Result<List<SysOssConfigVo>> list(SysOssConfigBo config) {
		PageQuery pageQuery = new PageQuery(10, 1);
		TableDataInfo<SysOssConfigVo> tableDataInfo = ossConfigService.queryPageList(config, pageQuery);
		List<SysOssConfigVo> rows = tableDataInfo.getRows();
		return Result.ok(rows);
	}

	@GetMapping("/page")
	public Result<Dict> page(SysOssConfigBo config, QueryPage queryPage) {
		PageQuery pageQuery = new PageQuery(queryPage.getLimit(), queryPage.getPage());
		TableDataInfo<SysOssConfigVo> tableDataInfo = ossConfigService.queryPageList(config, pageQuery);

		List<SysOssConfigVo> rows = tableDataInfo.getRows();
		long total = tableDataInfo.getTotal();
		Dict resultDict = Dict.create().set("rows", rows).set("total", (int) total);
		return Result.ok(resultDict);
	}

	@GetMapping("/{ossConfigId}")
	public Result<SysOssConfigVo> findById(@PathVariable Long ossConfigId) {
		return Result.ok(ossConfigService.queryById(ossConfigId));
	}

	/**
	 * 新增对象存储配置
	 */
	@SaCheckPermission("system:oss:config:add")
	@PostMapping()
	public Result add(@RequestBody SysOssConfigBo bo) {
		return Result.ok(ossConfigService.insertByBo(bo));
	}

	/**
	 * 修改对象存储配置
	 */
	@SaCheckPermission("system:oss:config:update")
	@PutMapping()
	public Result edit(@RequestBody SysOssConfigBo bo) {
		return Result.ok(ossConfigService.updateByBo(bo));
	}

	/**
	 * 删除对象存储配置
	 *
	 * @param ossConfigIds OSS配置ID串
	 */
	@SaCheckPermission("system:oss:config:delete")
	@DeleteMapping("/{ossConfigIds}")
	public Result remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ossConfigIds) {
		return Result.ok(ossConfigService.deleteWithValidByIds(List.of(ossConfigIds), true));
	}

}
