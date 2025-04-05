
package org.dromara.langchain.platform.service.impl;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.EmbeddingR;
import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.domain.AgiDocsSlice;
import org.dromara.langchain.platform.domain.constant.EmbedConst;
import org.dromara.langchain.platform.mapper.AgiDocsMapper;
import org.dromara.langchain.platform.provider.EmbeddingProvider;
import org.dromara.langchain.platform.service.AgiKnowledgeService;
import org.dromara.langchain.platform.service.EmbeddingService;
import org.dromara.langchain.platform.service.AgiEmbeddingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

	private final EmbeddingProvider embeddingProvider;
	private final AgiEmbeddingService langEmbeddingService;
	private final AgiKnowledgeService agiKnowledgeService;
	private final AgiDocsMapper agiDocsMapper;

	@Override
	@Transactional
	public void clearDocSlices(String docsId) {
		if (StrUtil.isBlank(docsId)) {
			return;
		}
		// remove from embedding store
		List<String> vectorIds = agiKnowledgeService.listSliceVectorIdsOfDoc(docsId);
		if (vectorIds.isEmpty()) {
			return;
		}
		AgiDocs docs = agiDocsMapper.selectById(docsId);
		EmbeddingStore<TextSegment> embeddingStore = embeddingProvider.getEmbeddingStore(docs.getKnowledgeId());
		embeddingStore.removeAll(vectorIds);
		// remove from docSlice
		agiKnowledgeService.removeSlicesOfDoc(docsId);
	}

	@Override
	public void embedDocsSlice(AgiDocs data, String url) {
		List<EmbeddingR> list = langEmbeddingService.embeddingDocs(
				new ChatReq().setDocsName(data.getName()).setKnowledgeId(data.getKnowledgeId()).setUrl(url));
		list.forEach(i -> {
			agiKnowledgeService
					.addDocsSlice(new AgiDocsSlice().setKnowledgeId(data.getKnowledgeId()).setDocsId(data.getId())
							.setVectorId(i.getVectorId()).setName(data.getName()).setContent(i.getText()));
		});

		agiKnowledgeService.updateDocs(new AgiDocs().setId(data.getId()).setSliceStatus(true).setSliceNum(list.size()));
	}

	@Override
	public List<Map<String, Object>> search(AgiDocs data) {
		if (StrUtil.isBlank(data.getKnowledgeId()) || StrUtil.isBlank(data.getContent())) {
			return List.of();
		}

		EmbeddingModel embeddingModel = embeddingProvider.getEmbeddingModel(data.getKnowledgeId());
		EmbeddingStore<TextSegment> embeddingStore = embeddingProvider.getEmbeddingStore(data.getKnowledgeId());
		Embedding queryEmbedding = embeddingModel.embed(data.getContent()).content();
		Filter filter = metadataKey(EmbedConst.KNOWLEDGE).isEqualTo(data.getKnowledgeId());
		EmbeddingSearchResult<TextSegment> list = embeddingStore
				.search(EmbeddingSearchRequest.builder().queryEmbedding(queryEmbedding).filter(filter).build());

		List<Map<String, Object>> result = new ArrayList<>();
		list.matches().forEach(i -> {
			TextSegment embedded = i.embedded();
			Map<String, Object> map = embedded.metadata().toMap();
			map.put("text", embedded.text());
			result.add(map);
		});
		return result;
	}
}
