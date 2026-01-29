package com.uos.all4runner.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AuthResponse {
	@Schema(name = "AuthResponse.Login")
	record Login(
		String accessToken
	){
	}

	@Schema(name = "AuthResponse.AccessToken")
	record AccessToken(
		String accessToken
	){
	}
}
