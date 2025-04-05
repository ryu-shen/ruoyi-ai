
package org.dromara.langchain.app.service;

import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.ImageR;
import org.dromara.langchain.system.domain.AgiSysOss;

public interface AgiChatService {

	void chat(ChatReq req);

	/**
	 * 文本请求
	 */
	String text(ChatReq req);

	/**
	 * 文生图
	 */
	AgiSysOss image(ImageR req);
}
