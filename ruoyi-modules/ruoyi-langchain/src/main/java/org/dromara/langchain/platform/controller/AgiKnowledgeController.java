package org.dromara.langchain.platform.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.common.core.domain.Result;
import org.dromara.langchain.common.util.MybatisUtil;
import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.domain.AgiEmbedStore;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.mapper.AgiDocsMapper;
import org.dromara.langchain.platform.provider.EmbeddingProvider;
import org.dromara.langchain.platform.provider.KnowledgeStoreFactory;
import org.dromara.langchain.platform.service.AgiEmbedStoreService;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agi/knowledge")
public class AgiKnowledgeController {

	private final AgiKnowledgeService kbService;
	private final AgiDocsMapper docsMapper;
	private final AgiEmbedStoreService embedStoreService;
	private final AgiModelService modelService;
	private final EmbeddingProvider embeddingProvider;
	private final KnowledgeStoreFactory knowledgeStore;

	@GetMapping("/list")
	public Result<List<AgiKnowledge>> list(AgiKnowledge data) {
		List<AgiKnowledge> list = kbService
				.list(Wrappers.<AgiKnowledge>lambdaQuery().orderByDesc(AgiKnowledge::getCreateTime));
		build(list);
		return Result.ok(list);
	}

	private void build(List<AgiKnowledge> records) {
		Map<String, List<AgiEmbedStore>> embedStoreMap = embedStoreService.list().stream()
				.collect(Collectors.groupingBy(AgiEmbedStore::getId));
		Map<String, List<AgiModel>> embedModelMap = modelService.list().stream()
				.collect(Collectors.groupingBy(AgiModel::getId));
		Map<String, List<AgiDocs>> docsMap = docsMapper.selectList(Wrappers.lambdaQuery()).stream()
				.collect(Collectors.groupingBy(AgiDocs::getKnowledgeId));
		records.forEach(item -> {
			List<AgiDocs> docs = docsMap.get(item.getId());
			if (docs != null) {
				item.setDocsNum(docs.size());
				item.setTotalSize(docs.stream().filter(d -> d.getSize() != null).mapToLong(AgiDocs::getSize).sum());
			}
			if (item.getEmbedModelId() != null) {
				List<AgiModel> list = embedModelMap.get(item.getEmbedModelId());
				item.setEmbedModel(list == null ? null : list.get(0));
			}
			if (item.getEmbedStoreId() != null) {
				List<AgiEmbedStore> list = embedStoreMap.get(item.getEmbedStoreId());
				item.setEmbedStore(list == null ? null : list.get(0));
			}
		});
	}

	@GetMapping("/page")
	public Result list(AgiKnowledge data, QueryPage queryPage) {
		Page<AgiKnowledge> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
		LambdaQueryWrapper<AgiKnowledge> queryWrapper = Wrappers.<AgiKnowledge>lambdaQuery()
				.like(!StrUtil.isBlank(data.getName()), AgiKnowledge::getName, data.getName())
				.orderByDesc(AgiKnowledge::getCreateTime);
		Page<AgiKnowledge> iPage = kbService.page(page, queryWrapper);

		build(iPage.getRecords());

		return Result.ok(MybatisUtil.getData(iPage));
	}

	@GetMapping("/{id}")
	public Result<AgiKnowledge> findById(@PathVariable String id) {
		AgiKnowledge knowledge = kbService.getById(id);
		if (knowledge.getEmbedStoreId() != null) {
			knowledge.setEmbedStore(embedStoreService.getById(knowledge.getEmbedStoreId()));
		}
		if (knowledge.getEmbedModelId() != null) {
			knowledge.setEmbedModel(modelService.getById(knowledge.getEmbedModelId()));
		}
		return Result.ok(knowledge);
	}

	@PostMapping
	@SaCheckPermission("agi:knowledge:add")
	public Result add(@RequestBody AgiKnowledge data) {
		data.setCreateTime(new Date());
		kbService.save(data);
		knowledgeStore.init();
		return Result.ok();
	}

	@PutMapping
	@SaCheckPermission("agi:knowledge:update")
	public Result update(@RequestBody AgiKnowledge data) {
		kbService.updateById(data);
		knowledgeStore.init();
		return Result.ok();
	}

	@DeleteMapping("/{id}")
	@SaCheckPermission("agi:knowledge:delete")
	public Result delete(@PathVariable String id) {
		kbService.removeKnowledge(id);
		knowledgeStore.init();
		return Result.ok();
	}
}
