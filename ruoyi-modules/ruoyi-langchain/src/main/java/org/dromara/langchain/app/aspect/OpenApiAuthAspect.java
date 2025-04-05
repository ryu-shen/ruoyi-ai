package org.dromara.langchain.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.dromara.common.core.exception.AgiServiceException;
import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.app.annotation.OpenApiAuth;
import org.dromara.langchain.app.component.AppChannelStore;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
@AllArgsConstructor
public class OpenApiAuthAspect {

	private final AppChannelStore channelStore;

	@Around("@annotation(openApiAuth)")
	public Object around(ProceedingJoinPoint point, OpenApiAuth openApiAuth) throws Throwable {
		String authorization = AgiLoginHelperUtil.getAuthorizationToken();
		if (authorization == null) {
			throw new AgiServiceException("Authentication Token invalid", 401);
		}

		String value = openApiAuth.value();
		channelStore.isExpired(value);
		return point.proceed();
	}

}