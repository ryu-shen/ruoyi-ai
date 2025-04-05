package org.dromara.langchain.common.domain.dto;

import dev.langchain4j.model.input.Prompt;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ImageR {

	private String modelId;
	private String modelName;
	private String modelProvider;

	private Prompt prompt;

	/**
	 * 内容
	 */
	private String message;

	/**
	 * 质量
	 */
	private String quality;

	/**
	 * 尺寸
	 */
	private String size;

	/**
	 * 风格
	 */
	private String style;
}
