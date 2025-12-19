package com.uos.all4runner.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountRole {
	MEMBER("일반 사용자"),
	ADMIN("관리자"),
	SUPERADMIN("최상위 관리자");
	private final String description;
}
