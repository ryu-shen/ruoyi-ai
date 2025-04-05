package org.dromara.langchain.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("langchain.chat")
public class ChatProps {
	/**
	 * 上下文的长度
	 */
	private Integer memoryMaxMessage = 20;

	/**
	 * 前端渲染的消息长度（过长会导致页面渲染卡顿）
	 */
	private Integer previewMaxMessage = 100;
}
