package org.dromara.common.core.domain;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code = HttpStatus.OK.value();

	private String message = HttpStatus.OK.getReasonPhrase();

	private T result;

	public Result() {
		super();
	}

	public Result(int code, String message) {
		this.code = code;
		this.message = message;
	}

	protected Result(T result) {
		this.result = result;
	}

	protected Result(HttpStatus httpStatus) {
		this.code = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();
	}

	protected Result(T result, HttpStatus httpStatus) {
		this.result = result;
		this.code = httpStatus.value();
		this.message = httpStatus.getReasonPhrase();
	}

	protected Result(Throwable e) {
		super();
		this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
		this.message = e.getMessage();
	}

	public static <T> Result<T> ok(T result) {
		return new Result(result);
	}

	public static <T> Result<T> ok(T result, HttpStatus httpStatus) {
		return new Result(result, httpStatus);
	}

	public static <T> Result<T> ok() {
		return new Result<>();
	}

	public static <T> Result<T> ok(String message) {
		return new Result<>(HttpStatus.OK.value(), message);
	}

	public static <T> Result<T> ok(HttpStatus httpStatus) {
		return new Result<>(httpStatus);
	}

	public static <T> Result<T> fail(String message) {
		return new Result<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}

	public static <T> Result<T> fail(int code, String message) {
		return new Result<>(code, message);
	}

	public static <T> Result<T> fail(HttpStatus status) {
		return new Result<>(status.value(), status.getReasonPhrase());
	}

	public static <T> Result<T> fail(Throwable e) {
		return new Result<>(e);
	}
}
