package org.dromara.langchain.platform.event;

import org.springframework.context.ApplicationEvent;

public class EmbeddingRefreshEvent extends ApplicationEvent {
	private static final long serialVersionUID = 4109980679877560773L;

	public EmbeddingRefreshEvent(Object source) {
		super(source);
	}
}
