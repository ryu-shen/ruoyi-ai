package org.dromara.langchain.system.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义定义 Log事件
 *
 */
public class LogEvent extends ApplicationEvent {

	public LogEvent(Object source) {
		super(source);
	}
}
