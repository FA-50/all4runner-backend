package com.uos.all4runner.service.auth;

import org.springframework.data.util.Pair;

import com.uos.all4runner.domain.dto.request.AccountRequest;

public interface AuthService {
	boolean checkDuplicateEmail(String email);

	Pair<String,String> LoginAccount(AccountRequest.Login request);
}
