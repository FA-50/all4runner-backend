package com.uos.all4runner.security;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;

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
	public boolean validate(String token){
		try {
			jwtDecoder.decode(token);
			return true;
		} catch (JwtException e) {
			return false;
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
