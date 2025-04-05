package org.dromara.langchain.platform.controller;

import java.util.List;

import org.dromara.common.core.utils.SpringUtils;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiEmbedStore;
import org.dromara.langchain.platform.event.EmbeddingRefreshEvent;
import org.dromara.langchain.platform.service.AgiEmbedStoreService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/embed-store")
public class AgiEmbedStoreController {

	private final AgiEmbedStoreService embedStoreService;

	@GetMapping("/list")
	public Result<List<AgiEmbedStore>> list(AgiEmbedStore data) {
		List<AgiEmbedStore> list = embedStoreService.list(Wrappers.lambdaQuery());
		list.forEach(this::hide);
		return Result.ok(list);
	}

	@GetMapping("/page")
	public Result<Dict> page(AgiEmbedStore embedStore, QueryPage queryPage) {
		IPage<AgiEmbedStore> page = embedStoreService.page(MybatisUtil.wrap(embedStore, queryPage),
				Wrappers.lambdaQuery());
		page.getRecords().forEach(this::hide);
		return Result.ok(MybatisUtil.getData(page));
	}

	@GetMapping("/{id}")
	public Result<AgiEmbedStore> findById(@PathVariable String id) {
		AgiEmbedStore store = embedStoreService.getById(id);
		hide(store);
		return Result.ok(store);
	}

	@PostMapping
	@SaCheckPermission("agi:embedstore:add")
	public Result<AgiEmbedStore> add(@RequestBody AgiEmbedStore data) {
		if (StrUtil.isNotBlank(data.getPassword()) && data.getPassword().contains("*")) {
			data.setPassword(null);
		}
		embedStoreService.save(data);
		SpringUtils.context().publishEvent(new EmbeddingRefreshEvent(data));
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:embedstore:update")
	public Result update(@RequestBody AgiEmbedStore data) {
		if (StrUtil.isNotBlank(data.getPassword()) && data.getPassword().contains("*")) {
			data.setPassword(null);
		}
		embedStoreService.updateById(data);
		SpringUtils.context().publishEvent(new EmbeddingRefreshEvent(data));
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:embedstore:delete")
	public Result delete(@PathVariable String id) {
		AgiEmbedStore store = embedStoreService.getById(id);
		if (store != null) {
			embedStoreService.removeById(id);
			SpringUtils.unregisterBean(id);
		}
		return Result.ok();
	}

	private void hide(AgiEmbedStore data) {
		if (data == null || StrUtil.isBlank(data.getPassword())) {
			return;
		}
		String key = StrUtil.hide(data.getPassword(), 0, data.getPassword().length());
		data.setPassword(key);
	}
}
