package com.uos.all4runner.security;

public final class SecurityPath {

	public static final String[] PUBLIC = {
		"/api/auth/**",
		"/api/health/**",
		"/swagger-ui/**",
		"/v3/api-docs/**"
	};

	public static final String[] ADMIN = {
		"/api/admin/**"
	};

}
