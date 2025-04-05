package org.dromara.langchain.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("langchain.auth")
public class AuthProps {
	/**
	 * salt
	 */
	private String saltKey = "langchain-salt";
}
