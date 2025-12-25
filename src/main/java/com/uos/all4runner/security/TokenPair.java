package com.uos.all4runner.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenPair {
	private final String accessToken;

	private final String refreshToken;

	public static TokenPair of(String accessToken, String refreshToken) {
		return new TokenPair(accessToken,refreshToken);
	}
}
