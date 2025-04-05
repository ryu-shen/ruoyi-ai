package org.dromara.langchain.app.service;

import java.util.List;

import org.dromara.langchain.app.domain.AgiConversation;
import org.dromara.langchain.app.domain.AgiMessage;
import org.dromara.langchain.common.domain.dto.QueryPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AgiMessageService extends IService<AgiMessage> {

	/**
	 * 获取会话列表
	 */
	List<AgiConversation> conversations(String userId);

	/**
	 * 获取会话分页列表
	 */
	IPage<AgiConversation> conversationPages(AgiConversation data, QueryPage queryPage);

	/**
	 * 新增会话
	 */
	AgiConversation addConversation(AgiConversation conversation);

	/**
	 * 修改会话
	 */
	void updateConversation(AgiConversation conversation);

	/**
	 * 删除会话
	 */
	void delConversation(String conversationId);

	AgiMessage addMessage(AgiMessage message);

	void clearMessage(String conversationId);

	List<AgiMessage> getMessages(String conversationId);

	List<AgiMessage> getMessages(String conversationId, String userId);
}
