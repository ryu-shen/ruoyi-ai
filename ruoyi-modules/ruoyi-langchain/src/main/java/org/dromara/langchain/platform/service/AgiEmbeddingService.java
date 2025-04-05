package org.dromara.langchain.platform.service;

import java.util.List;

import org.dromara.langchain.common.domain.dto.ChatReq;
import org.dromara.langchain.common.domain.dto.EmbeddingR;

public interface AgiEmbeddingService {

	EmbeddingR embeddingText(ChatReq req);

	List<EmbeddingR> embeddingDocs(ChatReq req);
}
