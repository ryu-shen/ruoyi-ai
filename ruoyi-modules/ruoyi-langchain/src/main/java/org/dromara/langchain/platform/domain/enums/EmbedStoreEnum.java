package org.dromara.langchain.platform.domain.enums;

import lombok.Getter;

@Getter
public enum EmbedStoreEnum {

	REDIS, PGVECTOR, MILVUS,;
}
