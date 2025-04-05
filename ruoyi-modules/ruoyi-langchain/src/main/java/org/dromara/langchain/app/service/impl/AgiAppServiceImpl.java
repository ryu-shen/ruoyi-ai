
package org.dromara.langchain.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.mapper.AgiAppMapper;
import org.dromara.langchain.app.service.AgiAppService;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AgiAppServiceImpl extends ServiceImpl<AgiAppMapper, AgiApp> implements AgiAppService {

	private final AgiModelService agiModelService;
	private final AgiKnowledgeService agiKnowledgeService;

	@Override
	public List<AgiApp> list(AgiApp data) {
		List<AgiApp> list = baseMapper.selectList(Wrappers.<AgiApp>lambdaQuery()
				.like(StrUtil.isNotBlank(data.getName()), AgiApp::getName, data.getName()));

		Map<String, List<AgiModel>> modelMap = agiModelService.list(new AgiModel()).stream()
				.collect(Collectors.groupingBy(AgiModel::getId));
		Map<String, List<AgiKnowledge>> knowledgeMap = agiKnowledgeService.list().stream()
				.collect(Collectors.groupingBy(AgiKnowledge::getId));
		list.forEach(i -> {
			List<AgiModel> models = modelMap.get(i.getModelId());
			if (models != null) {
				i.setModel(models.get(0));
			}
			if (i.getKnowledgeIds() != null) {
				List<AgiKnowledge> knowledges = new ArrayList<>();
				i.getKnowledgeIds().forEach(k -> {
					List<AgiKnowledge> items = knowledgeMap.get(k);
					if (items != null) {
						knowledges.add(items.get(0));
					}
				});
				i.setKnowledges(knowledges);
			}
		});
		return list;
	}

	@Override
	public AgiApp getById(String id) {
		AgiApp app = baseMapper.selectById(id);
		if (app != null) {
			String modelId = app.getModelId();
			if (modelId != null) {
				app.setModel(agiModelService.selectById(modelId));
			}
			List<String> knowledgeIds = app.getKnowledgeIds();
			if (knowledgeIds != null && !knowledgeIds.isEmpty()) {
				app.setKnowledges(agiKnowledgeService
						.list(Wrappers.<AgiKnowledge>lambdaQuery().in(AgiKnowledge::getId, knowledgeIds)));
			}
		}
		return app;
	}
}
