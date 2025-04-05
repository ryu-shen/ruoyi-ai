package org.dromara.common.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AgiServiceException extends RuntimeException {

	private static final long serialVersionUID = -1068765335343416833L;

	private final int code;

	public AgiServiceException(String message) {
		super(message);
		this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	public AgiServiceException(String message, int code) {
		super(message);
		this.code = code;
	}
}
