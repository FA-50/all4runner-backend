package com.uos.all4runner.constant;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	NO_ACCOUNT(HttpStatus.NO_CONTENT, "해당 계정이 존재하지 않습니다."),
	FAIL_LOGIN(HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호가 일치하지 않습니다."),
	NOT_ADMIN(HttpStatus.BAD_REQUEST, "어드민 계정이 아닙니다");

	private final HttpStatus status;
	private final String message;
}
