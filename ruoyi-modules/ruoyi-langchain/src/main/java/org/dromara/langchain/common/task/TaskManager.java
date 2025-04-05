package org.dromara.langchain.common.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class TaskManager {
	private static final ConcurrentHashMap<String, List<Future<?>>> TASK_MAP = new ConcurrentHashMap<>();

	/**
	 * 提交任务
	 */
	public static void submitTask(String id, Callable<?> function) {
		Future<?> future = AnalysisThreadPool.getThreadPool().submit(function);
		List<Future<?>> orDefault = TASK_MAP.getOrDefault(id, new ArrayList<>());
		orDefault.add(future);
		TASK_MAP.put(id, orDefault);
	}

	/**
	 * 弹出任务
	 */
	public void popTaskResult(String id) {
		TASK_MAP.remove(id);
	}

	public int getCount(String id) {
		if (TASK_MAP.containsKey(id)) {
			Collection<?> collection = TASK_MAP.get(id);
			return collection != null ? collection.size() : 0;
		}
		return 0;
	}
}
