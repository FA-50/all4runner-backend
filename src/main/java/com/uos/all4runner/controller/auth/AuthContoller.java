package com.uos.all4runner.controller.auth;

import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.uos.all4runner.common.api.ApiResult;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller implements AuthSwaggerSupoorter {

	private final AuthService authService;

	@Override
	@GetMapping("/duplicate-email/{email}")
	public ResponseEntity<ApiResult<Boolean>> isDuplicateEmail(
			@PathVariable String email
	) {
		return ApiResult.data(
			SuccessCode.REQUEST_SUCCESS,
			authService.checkDuplicateEmail(email)
		);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<Pair<String,String>> logIn(
		@RequestBody @Valid AccountRequest.Login login
	) {
		return ApiResult.token(
			SuccessCode.LOGIN_SUCCESS,
			authService.LoginAccount(login)
		);
	}
}
