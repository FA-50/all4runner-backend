package com.uos.all4runner.security;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.TokenValid;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.exception.CustomException;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.refreshtoken.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	private final JwtDecoder jwtDecoder;
	private final JwtEncoder jwtEncoder;
	private static final String ACCOUNTID_CLAIM_KEY = "accountId";
	private static final String ROLE_CLAIM_KEY = "role";
	private static final String EMAIL_CLAIM_KEY = "email";
	private final AccountRepository accountRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	public String issue(UUID accountId, Long expirationMinute){

		Account foundedAccount = accountRepository.findByIdOrThrow(accountId);

		JwtClaimsSet claims = JwtClaimsSet
			.builder()
			.subject(accountId.toString())
			.claim(ACCOUNTID_CLAIM_KEY,accountId.toString())
			.claim(ROLE_CLAIM_KEY, foundedAccount.getRole().toString())
			.claim(EMAIL_CLAIM_KEY, foundedAccount.getEmail())
			.issuer("all4runner")
			.issuedAt(Instant.now())
			.expiresAt(Instant.now().plusSeconds(60 * expirationMinute))
			.build();
		return jwtEncoder
			.encode(JwtEncoderParameters.from(claims))
			.getTokenValue();
	}

	public boolean validateAccessToken(String token) {
		try {
			jwtDecoder.decode(token);
			return true;
		} catch (JwtValidationException e) {
			// exp 만료, nbf 위반 등
			throw new CustomException(TokenValid.TIME_OUT);
		} catch (JwtException e) {
			// 서명 불일치, 포맷 오류 등
			throw new CustomException(TokenValid.INVALID);
		}
	}

	public UUID parseId(String token){
		return UUID.fromString(
			(String)jwtDecoder
				.decode(token)
				.getClaims()
				.get(ACCOUNTID_CLAIM_KEY)
		);
	}

	public DefaultCurrentUser getUserDetailFromToken(String token){
		Map<String,Object> claims =  jwtDecoder
			.decode(token)
			.getClaims();
		UUID accountId = UUID.fromString(
			claims.get(ACCOUNTID_CLAIM_KEY).toString()
		);
		String email = claims.get(EMAIL_CLAIM_KEY).toString();
		AccountRole role = AccountRole.valueOf(
			claims.get(ROLE_CLAIM_KEY).toString()
		);
		return new DefaultCurrentUser(
				accountId,
				email,
				role
		);
	}
}
