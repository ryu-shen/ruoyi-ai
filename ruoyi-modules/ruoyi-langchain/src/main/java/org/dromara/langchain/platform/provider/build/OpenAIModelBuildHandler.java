package org.dromara.langchain.platform.provider.build;

import java.time.Duration;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.common.domain.enums.ChatErrorEnum;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.enums.ProviderEnum;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class OpenAIModelBuildHandler implements ModelBuildHandler {

	/**
	 * 合并处理支持OpenAI接口的模型
	 */
	@Override
	public boolean whetherCurrentModel(AgiModel model) {
		String provider = model.getProvider();
		return ProviderEnum.OPENAI.name().equals(provider) || ProviderEnum.GEMINI.name().equals(provider)
				|| ProviderEnum.CLAUDE.name().equals(provider) || ProviderEnum.AZURE_OPENAI.name().equals(provider)
				|| ProviderEnum.DOUYIN.name().equals(provider) || ProviderEnum.YI.name().equals(provider)
				|| ProviderEnum.SILICON.name().equals(provider) || ProviderEnum.DEEPSEEK.name().equals(provider)
				|| ProviderEnum.SPARK.name().equals(provider);
	}

	@Override
	public boolean basicCheck(AgiModel model) {
		String apiKey = model.getApiKey();
		if (StrUtil.isBlank(apiKey)) {
			throw new AgiServiceException(
					ChatErrorEnum.API_KEY_IS_NULL.getErrorDesc(model.getProvider().toUpperCase(), model.getType()),
					ChatErrorEnum.API_KEY_IS_NULL.getErrorCode());
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
			return OpenAiStreamingChatModel.builder().apiKey(model.getApiKey()).baseUrl(model.getBaseUrl())
					.modelName(model.getModel()).maxTokens(model.getResponseLimit()).temperature(model.getTemperature())
					.logRequests(true).logResponses(true).topP(model.getTopP()).timeout(Duration.ofMinutes(10)).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(model.getProvider() + " Streaming Chat 模型配置报错", e);
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
			return OpenAiChatModel.builder().apiKey(model.getApiKey()).baseUrl(model.getBaseUrl())
					.modelName(model.getModel()).maxTokens(model.getResponseLimit()).temperature(model.getTemperature())
					.logRequests(true).logResponses(true).topP(model.getTopP()).timeout(Duration.ofMinutes(10)).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(model.getProvider() + " Chat 模型配置报错", e);
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
			OpenAiEmbeddingModel openAiEmbeddingModel = OpenAiEmbeddingModel.builder().apiKey(model.getApiKey())
					.baseUrl(model.getBaseUrl()).modelName(model.getModel()).dimensions(model.getDimension())
					.logRequests(true).logResponses(true).dimensions(1024).timeout(Duration.ofMinutes(10)).build();
			return openAiEmbeddingModel;
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(model.getProvider() + " Embedding 模型配置报错", e);
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
			return OpenAiImageModel.builder().apiKey(model.getApiKey()).baseUrl(model.getBaseUrl())
					.modelName(model.getModel()).size(model.getImageSize()).quality(model.getImageQuality())
					.style(model.getImageStyle()).logRequests(true).logResponses(true).timeout(Duration.ofMinutes(10))
					.build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(model.getProvider() + " Image 模型配置报错", e);
			return null;
		}

	}
}
