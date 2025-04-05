
package org.dromara.langchain.common.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatRes {

	private boolean isDone = false;

	private String message;

	private Integer usedToken;

	private long time;

	public ChatRes(String message) {
		this.message = message;
	}

	public ChatRes(Integer usedToken, long startTime) {
		this.isDone = true;
		this.usedToken = usedToken;
		this.time = System.currentTimeMillis() - startTime;
	}
}
