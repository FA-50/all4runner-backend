package com.uos.all4runner.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
	ACCOUNT_CREATE_SUCCESS(HttpStatus.CREATED, "계정이 정상적으로 생성되었습니다."),
	ACCOUNT_UPDATE_SUCCESS(HttpStatus.OK, "계정이 정상적으로 변경되었습니다."),
	ACCOUNT_RESPONSE_SUCCESS(HttpStatus.OK, "계정이 정상적으로 반환되었습니다.");
	private final HttpStatus status;
	private final String description;
}
