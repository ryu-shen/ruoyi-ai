package org.dromara.langchain.app.domain.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletionReq {

	private final String model;
	private final List<Message> messages;
	private final Double temperature;
	private final Double topP;
	private final Integer n;
	private final Boolean stream;
	private final List<String> stop;
	private final Integer maxTokens;
	private final Double presencePenalty;
	private final Double frequencyPenalty;
	private final String user;
	private final Integer seed;

	@Data
	@Builder
	public static class Message {
		String role;
		String content;
	}
}
