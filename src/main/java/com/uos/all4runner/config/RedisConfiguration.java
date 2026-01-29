package com.uos.all4runner.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
	// application.yml 에서 redis 관련 설정정보 가져오는 객체
	private final RedisProperties redisProperties;
	// RedissonClient 를 Spring Bean으로 등록
	// 이후 분산락에서 사용
	@Bean
	public RedissonClient redisClient() {
		Config config = new Config();
		// URI 설정
		String uri = String.format("redis://%s:%s", redisProperties.getHost(), redisProperties.getPort());
		config.useSingleServer().setAddress(uri);
		return Redisson.create(config);
	}
	// RedisConnectionFactory 를 return
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisProperties.getHost(),redisProperties.getPort());
	}
}