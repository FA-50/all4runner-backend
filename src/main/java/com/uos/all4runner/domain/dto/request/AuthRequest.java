package com.uos.all4runner.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public interface AuthRequest {
	@Schema(name = "AuthRequest.Login")
	record Login(
		@NotBlank String email,
		@NotBlank String password
	){
	}
}
