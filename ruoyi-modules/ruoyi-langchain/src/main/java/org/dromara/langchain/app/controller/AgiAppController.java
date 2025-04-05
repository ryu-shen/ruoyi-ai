package org.dromara.langchain.app.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.domain.Result;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.app.component.AppStore;
import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.domain.AgiAppApi;
import org.dromara.langchain.app.service.AgiAppApiService;
import org.dromara.langchain.app.service.AgiAppService;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/app")
public class AgiAppController {

	private final AgiAppService agiAppService;
	private final AgiAppApiService agiAppApiService;
	private final AppStore appStore;
	private final AgiKnowledgeService knowledgeService;

	@GetMapping("/channel/api/{appId}")
	public Result<AgiAppApi> getApiChanel(@PathVariable String appId) {
		List<AgiAppApi> list = agiAppApiService.list(Wrappers.<AgiAppApi>lambdaQuery().eq(AgiAppApi::getAppId, appId));
		return Result.ok(list.isEmpty() ? null : list.get(0));
	}

	@GetMapping("/list")
	public Result<List<AgiApp>> list(AgiApp data) {
		return Result.ok(agiAppService.list(data));
	}

	@GetMapping("/page")
	public Result<Dict> page(AgiApp data, QueryPage queryPage) {
		return Result.ok(MybatisUtil.getData(agiAppService.page(MybatisUtil.wrap(data, queryPage), Wrappers
				.<AgiApp>lambdaQuery().like(StringUtils.isNotEmpty(data.getName()), AgiApp::getName, data.getName()))));
	}

	@GetMapping("/{id}")
	public Result<AgiApp> findById(@PathVariable String id) {
		AgiApp app = agiAppService.getById(id);
		return Result.ok(app);
	}

	@PostMapping
	@SaCheckPermission("agi:app:add")
	public Result add(@RequestBody AgiApp data) {
		data.setCreateTime(new Date());
		data.setSaveTime(new Date());
		agiAppService.save(data);
		appStore.init();
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:app:update")
	public Result update(@RequestBody AgiApp data) {
		// 校验知识库是否是同纬度
		List<String> knowledgeIds = data.getKnowledgeIds();
		if (knowledgeIds != null && !knowledgeIds.isEmpty()) {
			List<AgiKnowledge> list = knowledgeService
					.list(Wrappers.<AgiKnowledge>lambdaQuery().in(AgiKnowledge::getId, knowledgeIds));
			Set<String> modelIds = new HashSet<>();
			Set<String> storeIds = new HashSet<>();
			list.forEach(know -> {
				modelIds.add(know.getEmbedModelId());
				storeIds.add(know.getEmbedStoreId());
			});
			if (modelIds.size() > 1) {
				throw new AgiServiceException("请选择相同向量库数据源配置的知识库");
			}
			if (storeIds.size() > 1) {
				throw new AgiServiceException("请选择相同向量模型配置的知识库");
			}
		}

		data.setSaveTime(new Date());
		agiAppService.updateById(data);
		appStore.init();
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:app:delete")
	public Result delete(@PathVariable String id) {
		agiAppService.removeById(id);
		appStore.init();
		return Result.ok();
	}
}