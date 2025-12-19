package com.uos.all4runner.exception;

import org.springframework.http.HttpStatus;

import com.uos.all4runner.constant.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private HttpStatus status;
	private String message;
	public CustomException(String message) { super(message); }
	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.message = errorCode.getMessage();
		this.status = errorCode.getStatus();
	}
}
