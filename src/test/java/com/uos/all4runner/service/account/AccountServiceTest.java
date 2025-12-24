package com.uos.all4runner.service.account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.AuthenticationCreation;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AccountServiceTest {

	@Autowired
	AccountService accountService;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	AccountRequest.Create accountDto;

	@BeforeEach
	void setUp() throws Exception {
		accountDto = AccountCreation.createAccountCreateRequest();
	}
	@AfterEach
	void tearDown() throws Exception {
		accountRepository.deleteAll();
	}

	@Test
	void 멤버계정생성성공_성공(){
		// when
		accountService.createMember(accountDto);
		Account foundedMember = accountRepository.findByName("멤버테스터").orElse(null);
		// then
		Assertions.assertNotNull(foundedMember);
		Assertions.assertTrue(passwordEncoder.matches("wjdu7471231", foundedMember.getPassword()));
	}

	@Test
	void 어드민계정생성_성공_슈퍼어드민(){
		// begin
		TestingAuthenticationToken testAuth_SUPERADMIN = AuthenticationCreation.createTestAuthentication_SUPERADMIN();
		SecurityContextHolder.getContext().setAuthentication(testAuth_SUPERADMIN);
		// when
		accountService.createAdmin(accountDto);
		// then
		Account foundedAccount = accountRepository.findByName("멤버테스터").orElse(null);
		Assertions.assertNotNull(foundedAccount);
		Assertions.assertEquals(AccountRole.ADMIN, foundedAccount.getRole());
	}

	@ParameterizedTest
	@ValueSource(strings = {"ROLE_ADMIN", "ROLE_MEMBER"})
	void 어드민계정생성_실패__멤버_어드민(String authority){
		// begin
		TestingAuthenticationToken testAuth = new TestingAuthenticationToken(
			new DefaultCurrentUser(
				UUID.randomUUID(),
				"admin@naver.com",
				null
			),
			null,
			authority
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when & then
		assertThrowsExactly(
			AuthorizationDeniedException.class,
			() -> accountService.createAdmin(accountDto)
		);
	}

}