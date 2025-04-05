package org.dromara.langchain.platform.provider;

import org.springframework.stereotype.Component;

import cn.hutool.core.util.ObjectUtil;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ModelProvider {

	private final ModelStoreFactory modelStoreFactory;

	public StreamingChatLanguageModel stream(String modelId) {
		StreamingChatLanguageModel streamingChatModel = modelStoreFactory.getStreamingChatModel(modelId);
		if (ObjectUtil.isNotEmpty(streamingChatModel)) {
			return streamingChatModel;
		}
		throw new RuntimeException("没有匹配到模型，请检查模型配置！");
	}

	public ChatLanguageModel text(String modelId) {
		ChatLanguageModel chatLanguageModel = modelStoreFactory.getChatLanguageModel(modelId);
		if (ObjectUtil.isNotEmpty(chatLanguageModel)) {
			return chatLanguageModel;
		}
		throw new RuntimeException("没有匹配到模型，请检查模型配置！");
	}

	public ImageModel image(String modelId) {
		ImageModel imageModel = modelStoreFactory.getImageModel(modelId);
		if (ObjectUtil.isNotEmpty(imageModel)) {
			return imageModel;
		}
		throw new RuntimeException("没有匹配到模型，请检查模型配置！");
	}
}
