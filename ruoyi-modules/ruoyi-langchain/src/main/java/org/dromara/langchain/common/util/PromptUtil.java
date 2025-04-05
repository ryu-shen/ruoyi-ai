package org.dromara.langchain.common.util;

import java.util.Map;

import org.dromara.langchain.common.domain.constant.PromptConst;

import cn.hutool.core.bean.BeanUtil;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

public class PromptUtil {

	public static Prompt build(String message) {
		return new Prompt(message);
	}

	public static Prompt build(String message, String promptText) {
		return new PromptTemplate(promptText + PromptConst.EMPTY).apply(Map.of(PromptConst.QUESTION, message));
	}

	public static Prompt build(String message, String promptText, Object param) {
		Map<String, Object> params = BeanUtil.beanToMap(param, false, true);
		params.put(PromptConst.QUESTION, message);
		return new PromptTemplate(promptText).apply(params);
	}

	public static Prompt buildDocs(String message) {
		return new PromptTemplate(PromptConst.DOCUMENT).apply(Map.of(PromptConst.QUESTION, message));
	}
}
