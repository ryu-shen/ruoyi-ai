package org.dromara.langchain.platform.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dromara.langchain.platform.domain.AgiEmbedStore;
import org.dromara.langchain.platform.domain.enums.EmbedStoreEnum;
import org.dromara.langchain.platform.service.AgiEmbedStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmbeddingStoreFactory {

	@Autowired
	private AgiEmbedStoreService agiEmbedStoreService;

	private final List<AgiEmbedStore> modelStore = new ArrayList<>();
	private final Map<String, EmbeddingStore<TextSegment>> embedStoreMap = new ConcurrentHashMap<>();

	@Async
	@PostConstruct
	public void init() {
		modelStore.clear();
		List<AgiEmbedStore> list = agiEmbedStoreService.list();
		list.forEach(embed -> {
			try {
				if (EmbedStoreEnum.REDIS.name().equalsIgnoreCase(embed.getProvider())) {
					RedisEmbeddingStore.Builder builder = RedisEmbeddingStore.builder().host(embed.getHost())
							.port(embed.getPort()).indexName(embed.getDatabaseName()).dimension(embed.getDimension());
					if (StrUtil.isNotBlank(embed.getUsername()) && StrUtil.isNotBlank(embed.getPassword())) {
						builder.user(embed.getUsername()).password(embed.getPassword());
					}
					EmbeddingStore<TextSegment> store = builder.build();
					embedStoreMap.put(embed.getId(), store);
				}
				if (EmbedStoreEnum.PGVECTOR.name().equalsIgnoreCase(embed.getProvider())) {
					EmbeddingStore<TextSegment> store = PgVectorEmbeddingStore.builder().host(embed.getHost())
							.port(embed.getPort()).database(embed.getDatabaseName()).dimension(embed.getDimension())
							.user(embed.getUsername()).password(embed.getPassword()).table(embed.getTableName())
							.indexListSize(1).useIndex(true).createTable(true).dropTableFirst(false).build();
					embedStoreMap.put(embed.getId(), store);
				}
				if (EmbedStoreEnum.MILVUS.name().equalsIgnoreCase(embed.getProvider())) {
					EmbeddingStore<TextSegment> store = MilvusEmbeddingStore.builder().host(embed.getHost())
							.port(embed.getPort()).databaseName(embed.getDatabaseName()).dimension(embed.getDimension())
							.username(embed.getUsername()).password(embed.getPassword())
							.collectionName(embed.getTableName()).build();
					embedStoreMap.put(embed.getId(), store);
				}
				modelStore.add(embed);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("向量数据库初始化失败：[{}] --- [{}]，数据库配置信息：[{}]", embed.getName(), embed.getProvider(), embed);
			}
		});

		modelStore.forEach(i -> log.info("已成功注册Embedding Store：{}， 配置信息：{}", i.getProvider(), i));
	}

	public EmbeddingStore<TextSegment> getEmbeddingStore(String embeddingId) {
		return embedStoreMap.get(embeddingId);
	}

	public boolean containsEmbeddingStore(String embeddingId) {
		return embedStoreMap.containsKey(embeddingId);
	}
}
