package org.dromara.langchain.system.event;

import org.dromara.langchain.system.domain.AgiSysLog;
import org.dromara.langchain.system.service.AgiSysLogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * 监听自定义Log 事件
 * 
 */
@Component
@RequiredArgsConstructor
public class LogListener {

	private final AgiSysLogService sysLogService;

	@Async
	@Order
	@EventListener(LogEvent.class)
	public void handler(LogEvent event) {
		AgiSysLog sysLog = (AgiSysLog) event.getSource();
		sysLogService.add(sysLog);
	}
}
