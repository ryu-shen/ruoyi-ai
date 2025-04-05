package org.dromara.langchain.app.service;

import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.ImageR;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.TokenStream;

public interface AppApiChatService {

	TokenStream chat(ChatReq req);

	TokenStream singleChat(ChatReq req);

	String text(ChatReq req);

	Response<Image> image(ImageR req);
}
