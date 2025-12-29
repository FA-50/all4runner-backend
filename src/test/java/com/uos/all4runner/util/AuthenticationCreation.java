package com.uos.all4runner.util;

import java.util.UUID;

import org.springframework.security.authentication.TestingAuthenticationToken;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.security.DefaultCurrentUser;

public class AuthenticationCreation {
	public static TestingAuthenticationToken createTestAuthentication(
		UUID accountId,
		AccountRole accountRole
	) {
		return new TestingAuthenticationToken(
			new DefaultCurrentUser(
				accountId,
				"admin@naver.com",
				accountRole
			),
			null,
			"ROLE_" + accountRole.name()
		);
	}
}
