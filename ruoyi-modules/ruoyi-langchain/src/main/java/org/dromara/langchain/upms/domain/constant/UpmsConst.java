package org.dromara.langchain.upms.domain.constant;

import org.dromara.common.core.constant.GlobalConstants;

public interface UpmsConst {

	/**
	 * 系统所有Redis缓存Key前缀 prefix
	 */
	String REDIS_KEY_PREFIX = GlobalConstants.GLOBAL_REDIS_KEY + "Authorization:";

	/**
	 * Auth缓存前缀
	 */
	String AUTH_PREFIX = REDIS_KEY_PREFIX + "login:";

	/**
	 * Auth Session缓存前缀
	 */
	String AUTH_SESSION_PREFIX = AUTH_PREFIX + "session:";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAIL_KEY = REDIS_KEY_PREFIX + "user_details";

}
