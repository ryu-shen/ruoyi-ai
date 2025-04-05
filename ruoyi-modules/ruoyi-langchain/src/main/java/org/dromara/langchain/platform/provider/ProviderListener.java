package org.dromara.langchain.platform.provider;

import org.dromara.langchain.platform.event.EmbeddingRefreshEvent;
import org.dromara.langchain.platform.event.ProviderRefreshEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ProviderListener {

	private final ModelStoreFactory providerInitialize;
	private final EmbeddingStoreFactory embeddingStoreInitialize;

	@EventListener
	public void providerEvent(ProviderRefreshEvent event) {
		log.info("refresh provider beans begin......");
		providerInitialize.init();
		log.info("refresh provider beans success......");
	}

	@EventListener
	public void providerEvent(EmbeddingRefreshEvent event) {
		log.info("refresh embedding beans begin......");
		embeddingStoreInitialize.init();
		log.info("refresh embedding beans success......");
	}
}
