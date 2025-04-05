package org.dromara.langchain.app.controller;

import java.util.List;

import org.dromara.common.core.domain.Result;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.core.utils.ServletUtils;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.app.domain.AgiConversation;
import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.app.service.AgiMessageService;
import org.dromara.langchain.common.domain.dto.QueryPage;
import org.dromara.langchain.common.util.MybatisUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/agi/conversation")
@AllArgsConstructor
public class AgiConversationController {

	private final AgiMessageService agiMessageService;

	/**
	 * conversation list, filter by user
	 */
	@GetMapping("/list")
	public Result conversations() {
		return Result.ok(agiMessageService.conversations(AgiLoginHelperUtil.getUserId()));
	}

	/**
	 * conversation page
	 */
	@GetMapping("/page")
	public Result list(AgiConversation data, QueryPage queryPage) {
		return Result.ok(MybatisUtil.getData(agiMessageService.conversationPages(data, queryPage)));
	}

	@PostMapping
	@SaCheckPermission("agi:conversation:add")
	public Result addConversation(@RequestBody AgiConversation conversation) {
		conversation.setUserId(AgiLoginHelperUtil.getUserId());
		return Result.ok(agiMessageService.addConversation(conversation));
	}

	@PutMapping
	@SaCheckPermission("agi:conversation:update")
	public Result updateConversation(@RequestBody AgiConversation conversation) {
		if (conversation.getId() == null) {
			throw new AgiServiceException("conversation id is null");
		}
		agiMessageService.updateConversation(conversation);
		return Result.ok();
	}

	@DeleteMapping("/{conversationId}")
	@SaCheckPermission("agi:conversation:delete")
	public Result delConversation(@PathVariable String conversationId) {
		agiMessageService.delConversation(conversationId);
		return Result.ok();
	}

	@DeleteMapping("/message/{conversationId}")
	@SaCheckPermission("agi:conversation:clear")
	public Result clearMessage(@PathVariable String conversationId) {
		agiMessageService.clearMessage(conversationId);
		return Result.ok();
	}

	/**
	 * get messages with conversationId
	 */
	@GetMapping("/messages/{conversationId}")
	public Result getMessages(@PathVariable String conversationId) {
		List<AgiMessage> list = agiMessageService.getMessages(conversationId);
		return Result.ok(list);
	}

	/**
	 * add message in conversation
	 */
	@PostMapping("/message")
	public Result addMessage(@RequestBody AgiMessage message) {
		message.setIp(ServletUtils.getClientIP());
		return Result.ok(agiMessageService.addMessage(message));
	}
}
