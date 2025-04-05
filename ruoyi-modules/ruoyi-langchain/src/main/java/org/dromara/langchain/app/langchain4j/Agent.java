package org.dromara.langchain.app.langchain4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

public interface Agent {

	TokenStream stream(@MemoryId String id, @UserMessage String message);

	String text(@MemoryId String id, @UserMessage String message);
}
