package org.dromara.langchain.platform.provider.build;

import org.dromara.langchain.platform.domain.AgiModel;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;

public interface ModelBuildHandler {

	/**
	 * 判断是不是当前模型
	 */
	boolean whetherCurrentModel(AgiModel model);

	/**
	 * basic check
	 */
	boolean basicCheck(AgiModel model);

	/**
	 * streaming chat build
	 */
	StreamingChatLanguageModel buildStreamingChat(AgiModel model);

	/**
	 * chat build
	 */
	ChatLanguageModel buildChatLanguageModel(AgiModel model);

	/**
	 * embedding config
	 */
	EmbeddingModel buildEmbedding(AgiModel model);

	/**
	 * image config
	 */
	ImageModel buildImage(AgiModel model);

}
