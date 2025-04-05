package org.dromara.langchain.app.langchain4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersistentChatMemoryStore implements ChatMemoryStore {

	private static final Map<Object, List<ChatMessage>> store = new HashMap<>();
	private static final Map<Object, Boolean> initSystemMessageStore = new HashMap<>();
	private static final Map<Object, Boolean> initMessageStore = new HashMap<>();

	public static void clean(Object memoryId) {
		log.info("clean message memory store to: {}", memoryId);
		store.remove(memoryId);
	}

	public static void init(Object memoryId, SystemMessage message) {
		Boolean isInitSystemMessage = initSystemMessageStore.get(memoryId);
		if (isInitSystemMessage != null && isInitSystemMessage) {
			return;
		}

		List<ChatMessage> list = store.get(memoryId);
		if (list == null) {
			store.put(memoryId, new ArrayList<>(List.of(message)));
		} else {
			list.add(message);
		}
		initSystemMessageStore.put(memoryId, true);
	}

	public static void init(Object memoryId, List<ChatMessage> messages) {
		log.info("initialize message memory store to: {}", memoryId);

		Boolean isInitMessage = initMessageStore.get(memoryId);
		if (isInitMessage != null && isInitMessage) {
			return;
		}

		List<ChatMessage> list = store.get(memoryId);
		if (list == null) {
			store.put(memoryId, messages);
		} else {
			list.addAll(messages);
		}
		initMessageStore.put(memoryId, true);
	}

	@Override
	public List<ChatMessage> getMessages(Object memoryId) {
		List<ChatMessage> list = store.get(memoryId);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	@Override
	public void updateMessages(Object memoryId, List<ChatMessage> messages) {
		store.put(memoryId, messages);
	}

	@Override
	public void deleteMessages(Object memoryId) {
		store.remove(memoryId);
	}
}
