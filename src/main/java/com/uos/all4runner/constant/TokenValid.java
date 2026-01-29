package com.uos.all4runner.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenValid {
	VALID("토큰이 유효합니다."),
	TIME_OUT("토큰이 만료되었습니다."),
	INVALID("토큰의 서명 불일치 / 포맷 깨짐 / Base64 디코딩 실패 / 구조 오류");
	private final String message;
}
