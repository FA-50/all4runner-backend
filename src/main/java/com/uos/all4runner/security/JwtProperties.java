package com.uos.all4runner.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jwt")
public record JwtProperties(
	String keyId,
	Long accessTokenExpiration,
	Long refreshTokenExpiration
) {}
