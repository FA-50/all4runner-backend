package com.uos.all4runner.constant;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 계정이 존재하지 않습니다."),
	REMOVED_ACCOUNT(HttpStatus.BAD_REQUEST, "삭제된 계정입니다."),
	FAIL_LOGIN(HttpStatus.BAD_REQUEST, "이메일 혹은 비밀번호가 일치하지 않습니다."),
	NOT_SUPERADMIN(HttpStatus.BAD_REQUEST, "슈퍼 관리자 계정이 아닙니다"),
	NOT_REMOVED(HttpStatus.BAD_REQUEST, "삭제된 계정이 아닙니다"),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"기존 비밀번호와 동일한 비밀번호가 아닙니다."),
	SAME_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 동일한 비밀번호입니다"),
	ACCOUNT_ACCESS_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "계정에 권한이 없습니다."),
	CANNOT_MODIFY_SUPERADMIN(HttpStatus.BAD_REQUEST, "슈퍼 관리자 계정을 수정/삭제할 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다. 관리자에게 문의하세요."),

	NODE_NOT_INCLUDE(HttpStatus.BAD_REQUEST, "경로 검색 시 활용할 노드가 포함되어있지 않습니다."),

	NODE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 위치에서 근접한 노드를 찾을 수 없습니다."),
	BUFFER_NOT_CREATED(HttpStatus.BAD_REQUEST, "설정된 거리에 해당하는 노드를 찾지 못해 버퍼를 생성할 수 없습니다.");
	private final HttpStatus status;
	private final String message;
}
