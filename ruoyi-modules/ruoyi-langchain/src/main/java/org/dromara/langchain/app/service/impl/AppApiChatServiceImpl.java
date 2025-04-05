package org.dromara.langchain.app.service.impl;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

import java.util.function.Function;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.app.langchain4j.Agent;
import org.dromara.langchain.app.langchain4j.PersistentChatMemoryStore;
import org.dromara.langchain.app.service.AppApiChatService;
import org.dromara.langchain.common.config.ChatProps;
import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.ImageR;
import org.dromara.langchain.common.util.PromptUtil;
import org.dromara.langchain.platform.domain.constant.EmbedConst;
import org.dromara.langchain.platform.provider.EmbeddingProvider;
import org.dromara.langchain.platform.provider.ModelProvider;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.store.embedding.filter.Filter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AppApiChatServiceImpl implements AppApiChatService {

	private final ModelProvider provider;
	private final EmbeddingProvider embeddingProvider;
	private final ChatProps chatProps;

	private AiServices<Agent> build(StreamingChatLanguageModel streamModel, ChatLanguageModel model, ChatReq req) {
		AiServices<Agent> aiServices = AiServices.builder(Agent.class)
				.chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder().id(req.getConversationId())
						.chatMemoryStore(new PersistentChatMemoryStore()).maxMessages(chatProps.getMemoryMaxMessage())
						.build());
		if (StrUtil.isNotBlank(req.getPromptText())) {
			aiServices.systemMessageProvider(memoryId -> req.getPromptText());
		}
		if (streamModel != null) {
			aiServices.streamingChatLanguageModel(streamModel);
		}
		if (model != null) {
			aiServices.chatLanguageModel(model);
		}
		return aiServices;
	}

	@Override
	public TokenStream chat(ChatReq req) {
		StreamingChatLanguageModel model = provider.stream(req.getModelId());
		if (StrUtil.isBlank(req.getConversationId())) {
			req.setConversationId(IdUtil.simpleUUID());
		}

		AiServices<Agent> aiServices = build(model, null, req);

		if (StrUtil.isNotBlank(req.getKnowledgeId())) {
			req.getKnowledgeIds().add(req.getKnowledgeId());
		}

		if (req.getKnowledgeIds() != null && !req.getKnowledgeIds().isEmpty()) {
			Function<Query, Filter> filter = (query) -> metadataKey(EmbedConst.KNOWLEDGE).isIn(req.getKnowledgeIds());
			ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
					.embeddingStore(embeddingProvider.getEmbeddingStore(req.getKnowledgeIds()))
					.embeddingModel(embeddingProvider.getEmbeddingModel(req.getKnowledgeIds())).dynamicFilter(filter)
					.build();
			aiServices
					.retrievalAugmentor(DefaultRetrievalAugmentor.builder().contentRetriever(contentRetriever).build());
		}
		Agent agent = aiServices.build();
		return agent.stream(req.getConversationId(), req.getMessage());
	}

	@Override
	public TokenStream singleChat(ChatReq req) {
		StreamingChatLanguageModel model = provider.stream(req.getModelId());
		if (StrUtil.isBlank(req.getConversationId())) {
			req.setConversationId(IdUtil.simpleUUID());
		}

		Agent agent = build(model, null, req).build();
		if (req.getPrompt() == null) {
			req.setPrompt(PromptUtil.build(req.getMessage(), req.getPromptText()));
		}
		return agent.stream(req.getConversationId(), req.getPrompt().text());
	}

	@Override
	public String text(ChatReq req) {
		if (StrUtil.isBlank(req.getConversationId())) {
			req.setConversationId(IdUtil.simpleUUID());
		}

		try {
			ChatLanguageModel model = provider.text(req.getModelId());
			Agent agent = build(null, model, req).build();
			String text = agent.text(req.getConversationId(), req.getMessage());
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Response<Image> image(ImageR req) {
		try {
			ImageModel model = provider.image(req.getModelId());
			return model.generate(req.getPrompt().text());
		} catch (Exception e) {
			e.printStackTrace();
			throw new AgiServiceException("图片生成失败");
		}
	}
}
