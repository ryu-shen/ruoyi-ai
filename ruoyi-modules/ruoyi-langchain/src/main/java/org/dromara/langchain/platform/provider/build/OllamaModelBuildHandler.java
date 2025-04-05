package org.dromara.langchain.platform.provider.build;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.common.domain.enums.ChatErrorEnum;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.enums.ProviderEnum;
import org.springframework.stereotype.Component;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OllamaModelBuildHandler implements ModelBuildHandler {

	@Override
	public boolean whetherCurrentModel(AgiModel model) {
		return ProviderEnum.OLLAMA.name().equals(model.getProvider());
	}

	@Override
	public boolean basicCheck(AgiModel model) {
		if (StringUtils.isBlank(model.getBaseUrl())) {
			throw new AgiServiceException(
					ChatErrorEnum.BASE_URL_IS_NULL.getErrorDesc(ProviderEnum.OLLAMA.name(), model.getType()),
					ChatErrorEnum.BASE_URL_IS_NULL.getErrorCode());
		}
		return true;
	}

	@Override
	public StreamingChatLanguageModel buildStreamingChat(AgiModel model) {
		try {
			if (!whetherCurrentModel(model)) {
				return null;
			}
			if (!basicCheck(model)) {
				return null;
			}
			return OllamaStreamingChatModel.builder().baseUrl(model.getBaseUrl()).modelName(model.getModel())
					.temperature(model.getTemperature()).topP(model.getTopP()).logRequests(true).logResponses(true)
					.timeout(Duration.ofMinutes(10)).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Ollama streaming chat 配置报错", e);
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
			return OllamaChatModel.builder().baseUrl(model.getBaseUrl()).modelName(model.getModel())
					.temperature(model.getTemperature()).topP(model.getTopP()).logRequests(true).logResponses(true)
					.timeout(Duration.ofMinutes(10)).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Ollama chat 配置报错", e);
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
			return OllamaEmbeddingModel.builder().baseUrl(model.getBaseUrl()).modelName(model.getModel())
					.logRequests(true).logResponses(true).timeout(Duration.ofMinutes(10)).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Ollama embedding 配置报错", e);
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
			log.error("Ollama image 配置报错", e);
			return null;
		}
	}
}
