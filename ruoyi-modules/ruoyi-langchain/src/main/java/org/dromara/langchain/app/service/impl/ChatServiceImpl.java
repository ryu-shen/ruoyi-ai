
package org.dromara.langchain.app.service.impl;

import org.dromara.common.core.utils.ServletUtils;
import org.dromara.langchain.app.component.AppStore;
import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.app.service.AgiChatService;
import org.dromara.langchain.app.service.AgiMessageService;
import org.dromara.langchain.app.service.AppApiChatService;
import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.ChatRes;
import org.dromara.langchain.common.domain.dto.ImageR;
import org.dromara.langchain.common.domain.enums.RoleEnum;
import org.dromara.langchain.common.util.StreamEmitterUtil;
import org.dromara.langchain.system.domain.AgiSysOss;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.output.TokenUsage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements AgiChatService {

	private final AppApiChatService appApiChatService;
	private final AgiMessageService agiMessageService;
	private final AppStore appStore;

	@Override
	public void chat(ChatReq req) {
		StreamEmitterUtil emitter = req.getEmitter();
		long startTime = System.currentTimeMillis();
		StringBuilder text = new StringBuilder();

		if (StrUtil.isNotBlank(req.getAppId())) {
			AgiApp app = appStore.get(req.getAppId());
			if (app != null) {
				req.setModelId(app.getModelId());
				req.setPromptText(app.getPrompt());
				req.setKnowledgeIds(app.getKnowledgeIds());
			}
		}

		// save user message
		req.setRole(RoleEnum.USER.getName());
		saveMessage(req, 0, 0);

		try {
			appApiChatService.chat(req).onNext(e -> {
				text.append(e);
				emitter.send(new ChatRes(e));
			}).onComplete((e) -> {
				TokenUsage tokenUsage = e.tokenUsage();
				ChatRes res = new ChatRes(tokenUsage.totalTokenCount(), startTime);
				emitter.send(res);
				emitter.complete();

				// save assistant message
				req.setMessage(text.toString());
				req.setRole(RoleEnum.ASSISTANT.getName());
				saveMessage(req, tokenUsage.inputTokenCount(), tokenUsage.outputTokenCount());
			}).onError((e) -> {
				emitter.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
			emitter.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	private void saveMessage(ChatReq req, Integer inputToken, Integer outputToken) {
		if (req.getConversationId() != null) {
			AgiMessage message = new AgiMessage();
			BeanUtils.copyProperties(req, message);
			message.setIp(ServletUtils.getClientIP());
			message.setPromptTokens(inputToken);
			message.setTokens(outputToken);
			agiMessageService.addMessage(message);
		}
	}

	@Override
	public String text(ChatReq req) {
		String text;
		try {
			text = appApiChatService.text(req);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return text;
	}

	@Override
	public AgiSysOss image(ImageR req) {
		Response<Image> res = appApiChatService.image(req);

		String path = res.content().url().toString();
		AgiSysOss oss = new AgiSysOss();
		oss.setUrl(path);
		return oss;
	}

}
