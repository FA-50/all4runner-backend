package com.uos.all4runner.controller.auth;

import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.AuthRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.domain.dto.response.AuthResponse;
import com.uos.all4runner.service.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthContoller implements AuthSwaggerSupoorter {

	private final AuthService authService;

	private static final String cookieName = "RT";

	@Override
	@GetMapping("/duplicate-email/{email}")
	public ResponseEntity<ApiResultResponse<Boolean>> isDuplicateEmail(
			@PathVariable String email
	) {
		return ApiResultResponse.data(
			SuccessCode.REQUEST_SUCCESS,
			authService.checkDuplicateEmail(email)
		);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<ApiResultResponse<AuthResponse.Login>> logIn(
		@RequestBody @Valid AuthRequest.Login login,
		HttpServletResponse response
	) {

		Pair<String,String> pairToken = authService.LoginAccount(login);
		// HttpOnly, Secure 쿠키 생성
		Cookie cookie = new Cookie(cookieName,pairToken.getSecond());
		cookie.setMaxAge(12*60*60); // 12시간
		cookie.setSecure(true); // HTTPS 에만 쿠키 전송
		cookie.setHttpOnly(true); // JS에서 접근 불가능
		cookie.setPath("/");
		response.addCookie(cookie);

		return ApiResultResponse.data(
			SuccessCode.LOGIN_SUCCESS,
			new AuthResponse.Login(
				pairToken.getFirst()
			)
		);
	}

	@PostMapping("/refresh")
	public ResponseEntity<ApiResultResponse<AuthResponse.AccessToken>> refresh(
		HttpServletRequest request
	){
		String refreshToken = resolveRefreshToken(request);

		String accessToken = authService.generateAccessToken(refreshToken);
		return ApiResultResponse.data(
			SuccessCode.ACCESS_TOKEN_REFRESH_SUCCESS,
			new AuthResponse.AccessToken(accessToken)
		);
	}
	public String resolveRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if( cookies != null ){
			for(Cookie cookie : cookies){
				if(cookieName.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
