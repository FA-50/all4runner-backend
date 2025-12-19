package com.uos.all4runner.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	FAIL_LOGIN(HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호가 일치하지 않습니다.");

	private final HttpStatus status;
	private final String message;
}
