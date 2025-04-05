package org.dromara.langchain.app.controller;

import java.util.List;

import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.langchain.app.annotation.OpenApiAuth;
import org.dromara.langchain.app.component.AppChannelStore;
import org.dromara.langchain.app.component.AppStore;
import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.domain.AgiAppApi;
import org.dromara.langchain.app.domain.constant.AppConst;
import org.dromara.langchain.app.domain.dto.CompletionReq;
import org.dromara.langchain.app.domain.dto.CompletionRes;
import org.dromara.langchain.app.service.AppApiChatService;
import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.util.StreamEmitterUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AppApiChatEndpoint {

	private final AppApiChatService appApiChatService;
	private final AppStore appStore;

	@OpenApiAuth(AppConst.CHANNEL_API)
	@PostMapping(value = "/chat/completions")
	public SseEmitter completions(@RequestBody CompletionReq req) {
		StreamEmitterUtil emitter = new StreamEmitterUtil();
		AgiAppApi appApi = AppChannelStore.getApiChannel();

		return handler(emitter, appApi.getAppId(), req.getMessages());
	}

	private SseEmitter handler(StreamEmitterUtil emitter, String appId, List<CompletionReq.Message> messages) {
		if (messages == null || messages.isEmpty() || StrUtil.isBlank(appId)) {
			throw new RuntimeException("聊天消息为空，或者没有配置模型信息");
		}
		CompletionReq.Message message = messages.get(0);

		AgiApp app = appStore.get(appId);
		if (app == null) {
			throw new AgiServiceException("没有配置应用信息");
		}
		ChatReq req = new ChatReq().setMessage(message.getContent()).setRole(message.getRole())
				.setModelId(app.getModelId()).setPromptText(app.getPrompt()).setKnowledgeIds(app.getKnowledgeIds());

		appApiChatService.singleChat(req).onNext(token -> {
			CompletionRes res = CompletionRes.process(token);
			emitter.send(res);
		}).onComplete(c -> {
			CompletionRes res = CompletionRes.end(c);
			emitter.send(res);
			emitter.complete();
		}).onError(e -> {
			emitter.error(e.getMessage());
		}).start();

		return emitter.get();
	}
}
