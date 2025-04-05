package org.dromara.langchain.platform.provider.build;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.common.domain.enums.ChatErrorEnum;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.enums.ProviderEnum;
import org.springframework.stereotype.Component;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QWenModelBuildHandler implements ModelBuildHandler {

	@Override
	public boolean whetherCurrentModel(AgiModel model) {
		return ProviderEnum.Q_WEN.name().equals(model.getProvider());
	}

	@Override
	public boolean basicCheck(AgiModel model) {
		if (StringUtils.isBlank(model.getApiKey())) {
			throw new AgiServiceException(
					ChatErrorEnum.API_KEY_IS_NULL.getErrorDesc(ProviderEnum.Q_WEN.name(), model.getType()),
					ChatErrorEnum.API_KEY_IS_NULL.getErrorCode());
		}
		return true;
	}

	@Override
	public StreamingChatLanguageModel buildStreamingChat(AgiModel model) {
		if (!whetherCurrentModel(model)) {
			return null;
		}
		if (!basicCheck(model)) {
			return null;
		}
		try {
			return QwenStreamingChatModel.builder().apiKey(model.getApiKey()).modelName(model.getModel())
					.baseUrl(model.getBaseUrl()).maxTokens(model.getResponseLimit())
					.temperature(Float.parseFloat(model.getTemperature().toString())).topP(model.getTopP()).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("qian wen streaming chat 配置报错", e);
			return null;
		}
	}

	@Override
	public ChatLanguageModel buildChatLanguageModel(AgiModel model) {
		try {
			if (!whetherCurrentModel(model)) {
				return null;
			}
			if (!basicCheck(model)) {
				return null;
			}
			return QwenChatModel.builder().apiKey(model.getApiKey()).modelName(model.getModel())
					.baseUrl(model.getBaseUrl()).enableSearch(true).maxTokens(model.getResponseLimit())
					.temperature(Float.parseFloat(model.getTemperature().toString())).topP(model.getTopP()).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("qian wen chat 配置报错", e);
			return null;
		}
	}

	@Override
	public EmbeddingModel buildEmbedding(AgiModel model) {
		try {
			if (!whetherCurrentModel(model)) {
				return null;
			}
			if (!basicCheck(model)) {
				return null;
			}
			return QwenEmbeddingModel.builder().apiKey(model.getApiKey()).modelName(model.getModel()).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("qian wen embedding 配置报错", e);
			return null;
		}
	}

	@Override
	public ImageModel buildImage(AgiModel model) {
		try {
			if (!whetherCurrentModel(model)) {
				return null;
			}
			if (!basicCheck(model)) {
				return null;
			}
			return null;
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("qian wen image 配置报错", e);
			return null;
		}

	}
}
