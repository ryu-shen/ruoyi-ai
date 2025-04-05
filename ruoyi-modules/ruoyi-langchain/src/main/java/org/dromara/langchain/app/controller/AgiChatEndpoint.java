package org.dromara.langchain.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dromara.common.core.domain.Result;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.app.langchain4j.PersistentChatMemoryStore;
import org.dromara.langchain.app.service.AgiAppService;
import org.dromara.langchain.app.service.AgiChatService;
import org.dromara.langchain.app.service.AgiMessageService;
import org.dromara.langchain.common.config.ChatProps;
import org.dromara.langchain.common.domain.constant.PromptConst;
import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.ChatRes;
import org.dromara.langchain.common.domain.dto.ImageR;
import org.dromara.langchain.common.domain.enums.RoleEnum;
import org.dromara.langchain.common.util.PromptUtil;
import org.dromara.langchain.common.util.StreamEmitterUtil;
import org.dromara.langchain.platform.domain.AgiModel;
import org.dromara.langchain.platform.service.AgiModelService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/agi")
@RestController
@AllArgsConstructor
public class AgiChatEndpoint {

	private final AgiChatService agiChatService;
	private final AgiMessageService messageService;
	private final AgiModelService agiModelService;
	private final AgiAppService appService;
	private final ChatProps chatProps;

	@PostMapping("/chat/completions")
	@SaCheckPermission("chat:completions")
	public SseEmitter chat(@RequestBody ChatReq req) {
		StreamEmitterUtil emitter = new StreamEmitterUtil();
		req.setEmitter(emitter);
		req.setUserId(AgiLoginHelperUtil.getUserId());
		req.setUsername(AgiLoginHelperUtil.getUsername());
		ExecutorService executor = Executors.newSingleThreadExecutor();
		req.setExecutor(executor);
		return emitter.streaming(executor, () -> {
			agiChatService.chat(req);
		});
	}

	@GetMapping("/app/info")
	public Result<AgiApp> appInfo(@RequestParam String appId, String conversationId) {
		AgiApp app = appService.getById(appId);
		if (StrUtil.isBlank(conversationId)) {
			conversationId = app.getId();
		}

		if (StrUtil.isNotBlank(app.getPrompt())) {
			SystemMessage message = new SystemMessage(app.getPrompt());
			PersistentChatMemoryStore.init(conversationId, message);
		}

		return Result.ok(app);
	}

	@GetMapping("/chat/messages/{conversationId}")
	public Result messages(@PathVariable String conversationId) {
		List<AgiMessage> list = messageService.getMessages(conversationId, AgiLoginHelperUtil.getUserId());

		// initialize chat memory
		List<ChatMessage> chatMessages = new ArrayList<>();
		list.forEach(item -> {
			if (chatMessages.size() >= chatProps.getMemoryMaxMessage()) {
				return;
			}
			if (item.getRole().equals(RoleEnum.ASSISTANT.getName())) {
				chatMessages.add(new AiMessage(item.getMessage()));
			} else {
				chatMessages.add(new UserMessage(item.getMessage()));
			}
		});
		PersistentChatMemoryStore.init(conversationId, chatMessages);
		return Result.ok(list);
	}

	@DeleteMapping("/chat/messages/clean/{conversationId}")
	@SaCheckPermission("chat:messages:clean")
	public Result cleanMessage(@PathVariable String conversationId) {
		messageService.clearMessage(conversationId);

		// clean chat memory
		PersistentChatMemoryStore.clean(conversationId);
		return Result.ok();
	}

	@PostMapping("/chat/mindmap")
	public Result mindmap(@RequestBody ChatReq req) {
		req.setPrompt(PromptUtil.build(req.getMessage(), PromptConst.MINDMAP));
		return Result.ok(new ChatRes(agiChatService.text(req)));
	}

	@PostMapping("/chat/image")
	public Result image(@RequestBody ImageR req) {
		req.setPrompt(PromptUtil.build(req.getMessage(), PromptConst.IMAGE));
		return Result.ok(agiChatService.image(req));
	}

	@GetMapping("/chat/getImageModels")
	public Result<List<AgiModel>> getImageModels() {
		List<AgiModel> list = agiModelService.getImageModels();
		list.forEach(i -> {
			i.setApiKey(null);
			i.setSecretKey(null);
		});
		return Result.ok(list);
	}
}
