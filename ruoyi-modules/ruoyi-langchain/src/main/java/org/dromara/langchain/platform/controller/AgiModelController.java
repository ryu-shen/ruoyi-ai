package org.dromara.langchain.platform.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.event.ProviderRefreshEvent;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/model")
public class AgiModelController {

	private final AgiModelService modelService;

	@GetMapping("/list")
	public Result<List<AgiModel>> list(AgiModel data) {
		return Result.ok(modelService.list(data));
	}

	@GetMapping("/page")
	public Result list(AgiModel data, QueryPage queryPage) {
		Page<AgiModel> iPage = modelService.page(data, queryPage);
		return Result.ok(MybatisUtil.getData(iPage));
	}

	@GetMapping("/{id}")
	public Result<AgiModel> findById(@PathVariable String id) {
		return Result.ok(modelService.selectById(id));
	}

	@PostMapping
	@SaCheckPermission("agi:model:add")
	public Result add(@RequestBody AgiModel data) {
		if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
			data.setApiKey(null);
		}
		if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
			data.setSecretKey(null);
		}
		modelService.save(data);
		SpringUtils.context().publishEvent(new ProviderRefreshEvent(data));
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:model:update")
	public Result update(@RequestBody AgiModel data) {
		if (StrUtil.isNotBlank(data.getApiKey()) && data.getApiKey().contains("*")) {
			data.setApiKey(null);
		}
		if (StrUtil.isNotBlank(data.getSecretKey()) && data.getSecretKey().contains("*")) {
			data.setSecretKey(null);
		}
		modelService.updateById(data);
		SpringUtils.context().publishEvent(new ProviderRefreshEvent(data));
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:model:delete")
	public Result delete(@PathVariable String id) {
		modelService.removeById(id);

		SpringUtils.unregisterBean(id);
		return Result.ok();
	}
}
