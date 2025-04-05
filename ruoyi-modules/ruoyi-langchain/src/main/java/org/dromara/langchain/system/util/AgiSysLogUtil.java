package org.dromara.langchain.system.util;

import java.util.Date;
import java.util.Objects;

import org.dromara.common.core.utils.SpringUtils;
import org.dromara.langchain.system.domain.AgiSysLog;
import org.dromara.langchain.system.event.LogEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.http.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

/**
 * 构建Log实体类信息
 *
 */
public class AgiSysLogUtil {

	/**
	 * 成功日志类型
	 */
	public static final int TYPE_OK = 1;
	/**
	 * 错误日志类型
	 */
	public static final int TYPE_FAIL = 2;

	/**
	 * Spring事件发布：发布日志，写入到数据库
	 *
	 * @param type      日志类型
	 * @param operation 描述
	 */
	public static void publish(int type, String operation, String username) {
		AgiSysLog sysLog = AgiSysLogUtil.build(type, operation, null, null, username);
		SpringUtils.context().publishEvent(new LogEvent(sysLog));
	}

	/**
	 * 构建日志Log类信息
	 *
	 * @param type      日志类型
	 * @param operation 日志描述
	 * @param method    操作方法
	 * @param time      耗时
	 * @return Log类
	 */
	@SneakyThrows
	public static AgiSysLog build(Integer type, String operation, String method, Long time, String username) {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

		return new AgiSysLog().setType(type).setUsername(username).setOperation(operation).setCreateTime(new Date())
				.setIp(JakartaServletUtil.getClientIP(request)).setUrl(URLUtil.getPath(request.getRequestURI()))
				.setMethod(method).setParams(HttpUtil.toParams(request.getParameterMap()))
				.setUserAgent(request.getHeader("user-agent")).setTime(time);
	}
}
