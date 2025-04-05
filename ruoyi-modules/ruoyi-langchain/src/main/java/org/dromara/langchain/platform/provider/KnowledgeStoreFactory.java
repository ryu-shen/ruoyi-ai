package org.dromara.langchain.platform.provider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.dromara.langchain.platform.domain.AgiEmbedStore;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.service.AgiEmbedStoreService;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KnowledgeStoreFactory {

	@Autowired
	private AgiKnowledgeService knowledgeService;
	@Autowired
	private AgiModelService modelService;
	@Autowired
	private AgiEmbedStoreService embedStoreService;

	private final Map<String, AgiKnowledge> knowledgeMap = new ConcurrentHashMap<>();

	@Async
	@PostConstruct
	public void init() {
		knowledgeMap.clear();
		List<AgiKnowledge> list = knowledgeService.list();
		Map<String, List<AgiModel>> modelMap = modelService.list().stream()
				.collect(Collectors.groupingBy(AgiModel::getId));
		Map<String, List<AgiEmbedStore>> storeMap = embedStoreService.list().stream()
				.collect(Collectors.groupingBy(AgiEmbedStore::getId));
		list.forEach(know -> {
			if (know.getEmbedModelId() != null) {
				List<AgiModel> models = modelMap.get(know.getEmbedModelId());
				know.setEmbedModel(models == null ? null : models.get(0));
			}
			if (know.getEmbedStoreId() != null) {
				List<AgiEmbedStore> stores = storeMap.get(know.getEmbedStoreId());
				know.setEmbedStore(stores == null ? null : stores.get(0));
			}
			knowledgeMap.put(know.getId(), know);
		});
	}

	public AgiKnowledge getKnowledge(String knowledgeId) {
		return knowledgeMap.get(knowledgeId);
	}

	public boolean containsKnowledge(String knowledgeId) {
		return knowledgeMap.containsKey(knowledgeId);
	}
}
