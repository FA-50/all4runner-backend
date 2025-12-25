package com.uos.all4runner.controller.auth;

import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.api.ApiResult;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "인증 관련 Api")
public interface AuthSwaggerSupoorter extends SwaggerSupoorter {

	@Operation(
		summary = "이메일 중복여부 확인",
		description = "해당 이메일로 사전에 등록된 계정이 존재하는지 확인하는 API",
		parameters = {
			@Parameter(name = "email" , description = "계정 이메일")
		}
	)
	ResponseEntity<ApiResult<Boolean>> isDuplicateEmail(String email);

	@Operation(
		summary = "로그인 기능 추가",
		description = "로그인을 수행하여 JWT 토큰을 발급받는 API"
	)
	ResponseEntity<ApiResult<AccountResponse.Login>>logIn(AccountRequest.Login login);
}
