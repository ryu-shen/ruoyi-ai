package org.dromara.langchain.platform.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.constant.ModelConst;
import org.dromara.langchain.platform.domain.enums.ModelTypeEnum;
import org.dromara.langchain.platform.provider.build.ModelBuildHandler;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

import cn.hutool.core.util.ObjectUtil;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ModelStoreFactory {

	@Autowired
	private AgiModelService agiModelService;
	@Autowired
	private List<ModelBuildHandler> modelBuildHandlers;

	private final List<AgiModel> modelStore = new ArrayList<>();
	private final Map<String, StreamingChatLanguageModel> streamingChatMap = new ConcurrentHashMap<>();
	private final Map<String, ChatLanguageModel> chatLanguageMap = new ConcurrentHashMap<>();
	private final Map<String, EmbeddingModel> embeddingModelMap = new ConcurrentHashMap<>();
	private final Map<String, ImageModel> imageModelMap = new ConcurrentHashMap<>();

	@Async
	@PostConstruct
	public void init() {
		modelStore.clear();
		streamingChatMap.clear();
		chatLanguageMap.clear();
		embeddingModelMap.clear();
		imageModelMap.clear();

		List<AgiModel> list = agiModelService.list();
		list.forEach(model -> {
			if (Objects.equals(model.getBaseUrl(), "")) {
				model.setBaseUrl(null);
			}

			chatHandler(model);
			embeddingHandler(model);
			imageHandler(model);
		});

		modelStore.forEach(i -> log.info("已成功注册模型：{} -- {}， 模型配置：{}", i.getProvider(), i.getType(), i));
	}

	private void chatHandler(AgiModel model) {
		try {
			String type = model.getType();
			if (!ModelTypeEnum.CHAT.name().equals(type)) {
				return;
			}
			modelBuildHandlers.forEach(x -> {
				StreamingChatLanguageModel streamingChatLanguageModel = x.buildStreamingChat(model);
				if (ObjectUtil.isNotEmpty(streamingChatLanguageModel)) {
					streamingChatMap.put(model.getId(), streamingChatLanguageModel);
					modelStore.add(model);
				}

				ChatLanguageModel languageModel = x.buildChatLanguageModel(model);
				if (ObjectUtil.isNotEmpty(languageModel)) {
					chatLanguageMap.put(model.getId() + ModelConst.TEXT_SUFFIX, languageModel);
				}
			});
		} catch (Exception e) {
			log.error("model 【 id: {} name: {}】streaming chat 配置报错", model.getId(), model.getName());
		}
	}

	private void embeddingHandler(AgiModel model) {
		try {
			String type = model.getType();
			if (!ModelTypeEnum.EMBEDDING.name().equals(type)) {
				return;
			}
			modelBuildHandlers.forEach(x -> {
				EmbeddingModel embeddingModel = x.buildEmbedding(model);
				if (ObjectUtil.isNotEmpty(embeddingModel)) {
					embeddingModelMap.put(model.getId(), embeddingModel);
					modelStore.add(model);
				}
			});

		} catch (Exception e) {
			log.error("model 【id{} name{}】 embedding 配置报错", model.getId(), model.getName());
		}
	}

	private void imageHandler(AgiModel model) {
		try {
			String type = model.getType();
			if (!ModelTypeEnum.TEXT_IMAGE.name().equals(type)) {
				return;
			}
			modelBuildHandlers.forEach(x -> {
				ImageModel imageModel = x.buildImage(model);
				if (ObjectUtil.isNotEmpty(imageModel)) {
					imageModelMap.put(model.getId(), imageModel);
					modelStore.add(model);
				}
			});
		} catch (Exception e) {
			log.error("model 【id{} name{}】 image 配置报错", model.getId(), model.getName());
		}
	}

	public StreamingChatLanguageModel getStreamingChatModel(String modelId) {
		return streamingChatMap.get(modelId);
	}

	public boolean containsStreamingChatModel(String modelId) {
		return streamingChatMap.containsKey(modelId);
	}

	public ChatLanguageModel getChatLanguageModel(String modelId) {
		return chatLanguageMap.get(modelId + ModelConst.TEXT_SUFFIX);
	}

	public boolean containsChatLanguageModel(String modelId) {
		return chatLanguageMap.containsKey(modelId + ModelConst.TEXT_SUFFIX);
	}

	public EmbeddingModel getEmbeddingModel(String modelId) {
		return embeddingModelMap.get(modelId);
	}

	public boolean containsEmbeddingModel(String modelId) {
		return embeddingModelMap.containsKey(modelId);
	}

	public ImageModel getImageModel(String modelId) {
		return imageModelMap.get(modelId);
	}

	public boolean containsImageModel(String modelId) {
		return imageModelMap.containsKey(modelId);
	}
}
