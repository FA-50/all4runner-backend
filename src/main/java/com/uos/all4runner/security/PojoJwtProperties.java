package com.uos.all4runner.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class PojoJwtProperties {
	private final JwtProperties jwt;
}
