package com.uos.all4runner.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
	REQUEST_SUCCESS(HttpStatus.OK, "요청이 정상적으로 수행되었습니다."),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인이 정상적으로 수행되었습니다."),
	ACCOUNT_CREATE_SUCCESS(HttpStatus.CREATED, "계정이 정상적으로 생성되었습니다."),
	ACCOUNT_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "계정이 정상적으로 반환되었습니다."),
	ACCOUNT_RESTORE_SUCCESS(HttpStatus.OK, "계정이 성공적으로 복구되었습니다."),
	ACCOUNT_UPDATE_SUCCESS(HttpStatus.OK, "계정이 성공적으로 수정되었습니다."),
	ACCOUNT_PASSWORD_UPDATE_SUCCESS(HttpStatus.OK, "계정이 성공적으로 수정되었습니다."),
	ACCOUNT_DELETE_SUCCESS(HttpStatus.OK, "계정이 정상적으로 삭제되었습니다.");
	private final HttpStatus status;
	private final String description;
}
