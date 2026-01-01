package com.uos.all4runner.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
	REQUEST_SUCCESS(HttpStatus.NO_CONTENT, "요청이 정상적으로 수행되었습니다."),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인이 정상적으로 수행되었습니다."),

	ACCOUNT_CREATE_SUCCESS(HttpStatus.CREATED, "계정이 정상적으로 생성되었습니다."),
	ACCOUNT_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "계정이 정상적으로 반환되었습니다."),
	ACCOUNT_RESTORE_SUCCESS(HttpStatus.NO_CONTENT, "계정이 성공적으로 복구되었습니다."),
	ACCOUNT_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "계정이 성공적으로 수정되었습니다."),
	ACCOUNT_PASSWORD_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "계정의 비밀번호가 성공적으로 수정되었습니다."),
	ACCOUNT_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "계정이 정상적으로 삭제되었습니다."),

	NODE_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "노드가 정상적으로 반환되었습니다."),
	
	TEMP_ROUTE_CREATE_SUCCESS(HttpStatus.CREATED, "임시경로가 정상적으로 생성되었습니다."),
	TEMP_ROUTE_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "임시경로가 공개경로로 정상적으로 등록되었습니다."),
	ROUTE_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "경로가 정상적으로 수정되었습니다."),
	TEMP_ROUTE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "계정의 모든 임시경로가 삭제되었습니다."),
	ROUTE_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "경로가 정상적으로 반환되었습니다."),
	ROUTE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "경로가 정상적으로 삭제되었습니다."),

	REVIEW_CREATE_SUCCESS(HttpStatus.CREATED, "리뷰가 정상적으로 생성되었습니다."),
	REVIEW_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "리뷰가 정상적으로 수정되었습니다."),
	REVIEW_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "리뷰가 정상적으로 삭제되었습니다."),
	REVIEW_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "리뷰가 정상적으로 반환되었습니다."),

	CATEGORY_CREATE_SUCCESS(HttpStatus.CREATED, "카테고리가 정상적으로 생성되었습니다."),
	CATEGORY_UPDATE_SUCCESS(HttpStatus.NO_CONTENT, "카테고리가 정상적으로 수정되었습니다."),
	CATEGORY_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "카테고리가 정상적으로 삭제되었습니다."),
	CATEGORY_DATA_RESPONSE_SUCCESS(HttpStatus.OK, "카테고리가 정상적으로 반환되었습니다."),
	;
	private final HttpStatus status;
	private final String description;
}
