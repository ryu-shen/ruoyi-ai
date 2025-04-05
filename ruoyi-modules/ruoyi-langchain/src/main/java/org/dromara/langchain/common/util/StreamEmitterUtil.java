package org.dromara.langchain.common.util;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class StreamEmitterUtil {

	private final SseEmitter emitter;

	public StreamEmitterUtil() {
		emitter = new SseEmitter(5 * 60 * 1000L);
	}

	public SseEmitter get() {
		return emitter;
	}

	public SseEmitter streaming(final ExecutorService executor, Runnable func) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();

		emitter.onCompletion(() -> {
			System.out.println("SseEmitter 完成");
			executor.shutdownNow();
		});

		emitter.onError((e) -> {
			System.out.println("SseEmitter 出现错误: " + e.getMessage());
			executor.shutdownNow();
		});

		emitter.onTimeout(() -> {
			System.out.println("SseEmitter 超时");
			emitter.complete();
			executor.shutdownNow();
		});
		executor.execute(() -> {
			try {
				func.run();
			} catch (Exception e) {
				System.out.println("捕获到异常: " + e.getMessage());
				emitter.completeWithError(e);
				Thread.currentThread().interrupt();
			} finally {
				if (!executor.isShutdown()) {
					executor.shutdownNow();
				}
			}
		});
		return emitter;
	}

	public void send(Object obj) {
		try {
			emitter.send(obj);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void complete() {
		emitter.complete();
	}

	public void error(String message) {
		try {
			emitter.send("Error: " + message);
			emitter.complete();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
