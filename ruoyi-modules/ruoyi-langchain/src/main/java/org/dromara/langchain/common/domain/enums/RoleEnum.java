package org.dromara.langchain.common.domain.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

	USER("user"), ASSISTANT("assistant"), SYSTEM("system"),;

	private final String name;

	RoleEnum(String name) {
		this.name = name;
	}
}
