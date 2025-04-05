package org.dromara.langchain.platform.provider.build;

import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.common.domain.enums.ChatErrorEnum;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.domain.enums.ProviderEnum;
import org.springframework.stereotype.Component;

import dev.langchain4j.community.model.qianfan.QianfanChatModel;
import dev.langchain4j.community.model.qianfan.QianfanEmbeddingModel;
import dev.langchain4j.community.model.qianfan.QianfanStreamingChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.image.ImageModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QFanModelBuildHandler implements ModelBuildHandler {

	@Override
	public boolean whetherCurrentModel(AgiModel model) {
		return ProviderEnum.Q_FAN.name().equals(model.getProvider());
	}

	@Override
	public boolean basicCheck(AgiModel model) {
		if (StringUtils.isBlank(model.getApiKey())) {
			throw new AgiServiceException(
					ChatErrorEnum.API_KEY_IS_NULL.getErrorDesc(ProviderEnum.Q_FAN.name(), model.getType()),
					ChatErrorEnum.API_KEY_IS_NULL.getErrorCode());
		}
		if (StringUtils.isBlank(model.getSecretKey())) {
			throw new AgiServiceException(
					ChatErrorEnum.SECRET_KEY_IS_NULL.getErrorDesc(ProviderEnum.Q_FAN.name(), model.getType()),
					ChatErrorEnum.SECRET_KEY_IS_NULL.getErrorCode());
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
			return QianfanStreamingChatModel.builder().apiKey(model.getApiKey()).secretKey(model.getSecretKey())
					.modelName(model.getModel()).baseUrl(model.getBaseUrl()).temperature(model.getTemperature())
					.topP(model.getTopP()).logRequests(true).logResponses(true).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Qianfan  streaming chat 配置报错", e);
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
			return QianfanChatModel.builder().apiKey(model.getApiKey()).secretKey(model.getSecretKey())
					.modelName(model.getModel()).baseUrl(model.getBaseUrl()).temperature(model.getTemperature())
					.topP(model.getTopP()).logRequests(true).logResponses(true).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Qianfan chat 配置报错", e);
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
			return QianfanEmbeddingModel.builder().apiKey(model.getApiKey()).modelName(model.getModel())
					.secretKey(model.getSecretKey()).logRequests(true).logResponses(true).build();
		} catch (AgiServiceException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Qianfan embedding 配置报错", e);
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
			log.error("Qianfan image 配置报错", e);
			return null;
		}

	}
}
