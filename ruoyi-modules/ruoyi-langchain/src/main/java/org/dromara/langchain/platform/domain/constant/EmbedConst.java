package org.dromara.langchain.platform.domain.constant;

public interface EmbedConst {

	String ORIGIN_TYPE_INPUT = "INPUT";
	String ORIGIN_TYPE_UPLOAD = "UPLOAD";

	String KNOWLEDGE = "knowledgeId";
	String FILENAME = "docsName";

	String CLAZZ_NAME_OPENAI = "OpenAiEmbeddingModel";
	String CLAZZ_NAME_QIANFAN = "QianfanEmbeddingModel";
	String CLAZZ_NAME_QIANWEN = "QwenEmbeddingModel";
	String CLAZZ_NAME_ZHIPU = "ZhipuAiEmbeddingModel";
	String CLAZZ_NAME_OLLAMA = "OllamaEmbeddingModel";
}
