package com.uos.all4runner.util;

import java.util.UUID;

import org.springframework.security.authentication.TestingAuthenticationToken;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.security.DefaultCurrentUser;

public class AuthenticationCreation {
	public static TestingAuthenticationToken createTestAuthentication_MEMBER(){
		return new TestingAuthenticationToken(
			new DefaultCurrentUser(
				UUID.randomUUID(),
				"admin@naver.com",
				null
			),
			null,
			"ROLE_MEMBER"
		);
	}

	public static TestingAuthenticationToken createTestAuthentication_ADMIN(){
		return new TestingAuthenticationToken(
			new DefaultCurrentUser(
				UUID.randomUUID(),
				"admin@naver.com",
				null
			),
			null,
			"ROLE_ADMIN"
		);
	}

	public static TestingAuthenticationToken createTestAuthentication_SUPERADMIN(){
		return new TestingAuthenticationToken(
			new DefaultCurrentUser(
				UUID.randomUUID(),
				"admin@naver.com",
				null
			),
			null,
			"ROLE_SUPERADMIN"
		);
	}
}
