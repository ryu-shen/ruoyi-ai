
package org.dromara.langchain.common.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.dromara.langchain.common.util.StreamEmitterUtil;

import dev.langchain4j.model.input.Prompt;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatReq {

	private String appId;
	private String modelId;
	private String modelName;
	private String modelProvider;

	private String message;

	private String conversationId;

	private String userId;

	private String username;

	private String chatId;

	private String promptText;

	private String docsName;

	private String knowledgeId;
	private List<String> knowledgeIds = new ArrayList<>();

	private String docsId;

	private String url;

	private String role;

	private Prompt prompt;

	private StreamEmitterUtil emitter;
	private Executor executor;
}
