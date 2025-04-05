package org.dromara.langchain.platform.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.platform.domain.AgiKnowledge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class EmbeddingProvider {

	private final EmbeddingStoreFactory embeddingStoreFactory;
	private final KnowledgeStoreFactory knowledgeStoreFactory;
	private final ModelStoreFactory modelStoreFactory;

	public static DocumentSplitter splitter() {
		return DocumentSplitters.recursive(300, 20);
	}

	public EmbeddingModel getEmbeddingModel(List<String> knowledgeIds) {
		List<String> storeIds = new ArrayList<>();
		knowledgeIds.forEach(id -> {
			if (knowledgeStoreFactory.containsKnowledge(id)) {
				AgiKnowledge data = knowledgeStoreFactory.getKnowledge(id);
				if (data.getEmbedModelId() != null) {
					storeIds.add(data.getEmbedModelId());
				}
			}
		});
		if (storeIds.isEmpty()) {
			throw new AgiServiceException("知识库缺少Embedding Model配置，请先检查配置");
		}

		HashSet<String> filterIds = new HashSet<>(storeIds);
		if (filterIds.size() > 1) {
			throw new AgiServiceException("存在多个不同Embedding Model的知识库，请先检查配置");
		}

		return modelStoreFactory.getEmbeddingModel(storeIds.get(0));
	}

	public EmbeddingModel getEmbeddingModel(String knowledgeId) {
		if (knowledgeStoreFactory.containsKnowledge(knowledgeId)) {
			AgiKnowledge data = knowledgeStoreFactory.getKnowledge(knowledgeId);
			if (modelStoreFactory.containsEmbeddingModel(data.getEmbedModelId())) {
				return modelStoreFactory.getEmbeddingModel(data.getEmbedModelId());
			}
		}
		throw new AgiServiceException("没有找到匹配的Embedding向量数据库");
	}

	public EmbeddingStore<TextSegment> getEmbeddingStore(String knowledgeId) {
		if (knowledgeStoreFactory.containsKnowledge(knowledgeId)) {
			AgiKnowledge data = knowledgeStoreFactory.getKnowledge(knowledgeId);
			if (embeddingStoreFactory.containsEmbeddingStore(data.getEmbedStoreId())) {
				return embeddingStoreFactory.getEmbeddingStore(data.getEmbedStoreId());
			}
		}
		throw new AgiServiceException("没有找到匹配的Embedding向量数据库");
	}

	public EmbeddingStore<TextSegment> getEmbeddingStore(List<String> knowledgeIds) {
		List<String> storeIds = new ArrayList<>();
		knowledgeIds.forEach(id -> {
			if (knowledgeStoreFactory.containsKnowledge(id)) {
				AgiKnowledge data = knowledgeStoreFactory.getKnowledge(id);
				if (data.getEmbedStoreId() != null) {
					storeIds.add(data.getEmbedStoreId());
				}
			}
		});
		if (storeIds.isEmpty()) {
			throw new AgiServiceException("知识库缺少Embedding Store配置，请先检查配置");
		}

		HashSet<String> filterIds = new HashSet<>(storeIds);
		if (filterIds.size() > 1) {
			throw new AgiServiceException("存在多个不同Embedding Store数据源的知识库，请先检查配置",
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		return embeddingStoreFactory.getEmbeddingStore(storeIds.get(0));
	}

}
