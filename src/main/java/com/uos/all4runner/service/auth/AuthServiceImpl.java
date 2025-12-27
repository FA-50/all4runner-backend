package com.uos.all4runner.service.auth;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.security.JwtService;
import com.uos.all4runner.security.PojoJwtProperties;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final PojoJwtProperties pojoJwtProperties;

	@Override
	public boolean checkDuplicateEmail(String email) {
		Account foundedAccount = accountRepository.findByEmail(email).orElse(null);
		return foundedAccount != null;
	}

	@Override
	public Pair<String,String> LoginAccount(AccountRequest.Login request) {
		Account foundedAccount = accountRepository.findByEmailOrThrow(request.email());

		PreConditions.validate(
			passwordEncoder.matches(request.password(),foundedAccount.getPassword()),
			ErrorCode.FAIL_LOGIN
		);

		PreConditions.validate(
			foundedAccount.getStatus().equals(AccountStatus.ACTIVATED),
			ErrorCode.REMOVED_ACCOUNT
		);

		String accessToken = jwtService.issue(
			foundedAccount.getId(),
			pojoJwtProperties.getJwt().accessTokenExpiration()
		);

		String refreshToken = jwtService.issue(
			foundedAccount.getId(),
			pojoJwtProperties.getJwt().refreshTokenExpiration()
		);

		return Pair.of(accessToken, refreshToken);
	}
}
