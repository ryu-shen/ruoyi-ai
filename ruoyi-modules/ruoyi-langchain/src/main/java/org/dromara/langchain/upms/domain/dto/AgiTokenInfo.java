package org.dromara.langchain.upms.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AgiTokenInfo<T> {

	/**
	 * Token
	 */
	private String token;

	/**
	 * 过期时间
	 */
	private Long expiration;

	/**
	 * 用户信息
	 */
	private T user;
}
