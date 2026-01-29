package com.uos.all4runner.service.auth;

import org.springframework.data.util.Pair;

import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.request.AuthRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
	boolean checkDuplicateEmail(String email);

	Pair<String,String> LoginAccount(AuthRequest.Login request);

	String generateAccessToken(String refreshToken);
}
