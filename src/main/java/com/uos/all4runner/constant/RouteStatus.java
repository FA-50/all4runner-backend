package com.uos.all4runner.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RouteStatus {
	PUBLIC("공개경로"),
	PRIVATE("비공개경로"),
	TEMP("임시경로");
	private final String description;
}
