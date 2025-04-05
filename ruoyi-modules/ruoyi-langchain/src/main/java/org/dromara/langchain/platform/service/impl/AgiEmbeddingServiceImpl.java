package org.dromara.langchain.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.EmbeddingR;
import org.dromara.langchain.platform.domain.constant.EmbedConst;
import org.dromara.langchain.platform.provider.EmbeddingProvider;
import org.dromara.langchain.platform.service.AgiEmbeddingService;
import org.springframework.stereotype.Service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AgiEmbeddingServiceImpl implements AgiEmbeddingService {

	private final EmbeddingProvider embeddingProvider;

	@Override
	public EmbeddingR embeddingText(ChatReq req) {
		log.info(">>>>>>>>>>>>>> Text文本向量解析开始，KnowledgeId={}, DocsName={}", req.getKnowledgeId(), req.getDocsName());
		TextSegment segment = TextSegment.from(req.getMessage(), Metadata
				.metadata(EmbedConst.KNOWLEDGE, req.getKnowledgeId()).put(EmbedConst.FILENAME, req.getDocsName()));

		EmbeddingModel embeddingModel = embeddingProvider.getEmbeddingModel(req.getKnowledgeId());
		EmbeddingStore<TextSegment> embeddingStore = embeddingProvider.getEmbeddingStore(req.getKnowledgeId());
		Embedding embedding = embeddingModel.embed(segment).content();
		String id = embeddingStore.add(embedding, segment);

		log.info(">>>>>>>>>>>>>> Text文本向量解析结束，KnowledgeId={}, DocsName={}", req.getKnowledgeId(), req.getDocsName());
		return new EmbeddingR().setVectorId(id).setText(segment.text());
	}

	@Override
	public List<EmbeddingR> embeddingDocs(ChatReq req) {
		log.info(">>>>>>>>>>>>>> Docs文档向量解析开始，KnowledgeId={}, DocsName={}", req.getKnowledgeId(), req.getDocsName());
		Document document = UrlDocumentLoader.load(req.getUrl(), new ApacheTikaDocumentParser());
		document.metadata().put(EmbedConst.KNOWLEDGE, req.getKnowledgeId()).put(EmbedConst.FILENAME, req.getDocsName());

		List<EmbeddingR> list = new ArrayList<>();
		try {
			DocumentSplitter splitter = EmbeddingProvider.splitter();
			List<TextSegment> segments = splitter.split(document);

			EmbeddingModel embeddingModel = embeddingProvider.getEmbeddingModel(req.getKnowledgeId());
			EmbeddingStore<TextSegment> embeddingStore = embeddingProvider.getEmbeddingStore(req.getKnowledgeId());
			List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
			List<String> ids = embeddingStore.addAll(embeddings, segments);

			for (int i = 0; i < ids.size(); i++) {
				list.add(new EmbeddingR().setVectorId(ids.get(i)).setText(segments.get(i).text()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info(">>>>>>>>>>>>>> Docs文档向量解析结束，KnowledgeId={}, DocsName={}", req.getKnowledgeId(), req.getDocsName());
		return list;
	}
}
