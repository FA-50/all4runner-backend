package com.uos.all4runner.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountStatus {
	ACTIVATED("활성"),
	REMOVED("탈퇴");
	private final String description;
}
