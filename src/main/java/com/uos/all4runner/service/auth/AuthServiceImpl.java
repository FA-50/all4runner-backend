package com.uos.all4runner.service.auth;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.request.AuthRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.refreshtoken.RefreshTokenRepository;
import com.uos.all4runner.security.JwtService;
import com.uos.all4runner.security.PojoJwtProperties;
import com.uos.all4runner.security.RefreshToken;
import com.uos.all4runner.util.PreConditions;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final PojoJwtProperties pojoJwtProperties;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public boolean checkDuplicateEmail(String email) {
		Account foundedAccount = accountRepository.findByEmail(email).orElse(null);
		return foundedAccount != null;
	}

	@Override
	public Pair<String,String> LoginAccount(AuthRequest.Login request) {
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

		refreshTokenRepository.save(
			new RefreshToken(
				refreshToken,
				foundedAccount.getId(),
				foundedAccount.getEmail()
				)
		);
		return Pair.of(accessToken, refreshToken);
	}

	@Override
	public String generateAccessToken(String refreshToken) {
		PreConditions.validate(
			Strings.isNotBlank(refreshToken),
			ErrorCode.REFRESH_TOKEN_NOT_FOUND
		);
		// Client에서 전송한 RefreshToken으로 Redis DB에 저장된 RefreshToken 조회
		RefreshToken foundedRefreshToken = refreshTokenRepository
			.findByRefreshTokenByIdOrThrow(refreshToken);
		//
		return jwtService.issue(
			foundedRefreshToken.getId(),
			pojoJwtProperties.getJwt().accessTokenExpiration()
		);
	}


}
