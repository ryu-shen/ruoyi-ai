package org.dromara.langchain.app.domain.dto;

import java.util.List;

import dev.langchain4j.model.output.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletionRes {

	private final String id;
	private final Integer created;
	private final String model;
	private final List<ChatCompletionChoice> choices;
	private final Usage usage;

	public static CompletionRes process(String token) {
		return CompletionRes.builder()
				.choices(List.of(ChatCompletionChoice.builder().delta(Delta.builder().content(token).build()).build()))
				.build();
	}

	public static CompletionRes end(Response res) {
		return CompletionRes.builder()
				.usage(Usage.builder().completionTokens(res.tokenUsage().outputTokenCount())
						.promptTokens(res.tokenUsage().inputTokenCount())
						.totalTokens(res.tokenUsage().totalTokenCount()).build())
				.choices(List.of(ChatCompletionChoice.builder()
						.finishReason(res.finishReason() == null ? "finish" : res.finishReason().toString()).build()))
				.build();
	}

	@Data
	@Builder
	static class Usage {
		private final Integer promptTokens;
		private final Integer completionTokens;
		private final Integer totalTokens;
	}

	@Data
	@Builder
	static class ChatCompletionChoice {
		private final Delta delta;
		private final String finishReason;
	}

	@Data
	@Builder
	static class Delta {
		private final String content;
	}
}
