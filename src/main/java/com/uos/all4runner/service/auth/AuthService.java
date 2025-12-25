package com.uos.all4runner.service.auth;

import org.springframework.data.util.Pair;

import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.security.TokenPair;

public interface AuthService {
	boolean checkDuplicateEmail(String email);

	TokenPair LoginAccount(AccountRequest.Login request);
}
