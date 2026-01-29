package com.uos.all4runner.security;


import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;
@RedisHash(value = "refreshToken", timeToLive = 43200) // 12시간
@NoArgsConstructor
@Getter
@ToString
public class RefreshToken {
	@Id
	private String refreshToken;
	private UUID id;
	private String userId;
	public RefreshToken(String refreshToken, UUID id, String userId) {
		this.refreshToken = refreshToken;
		this.id = id;
		this.userId = userId;
	}
}
