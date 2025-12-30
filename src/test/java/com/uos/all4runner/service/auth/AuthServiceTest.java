package com.uos.all4runner.service.auth;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.exception.CustomException;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.security.JwtService;
import com.uos.all4runner.util.AccountCreation;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AuthServiceTest {
	@Autowired
	AuthService authService;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	JwtService jwtService;

	Account testMemberAccount;

	@BeforeEach
	void setUp() throws Exception {
		testMemberAccount = AccountCreation.createMember();
		accountRepository.save(testMemberAccount);
	}

	@AfterEach
	void tearDown() throws Exception {
		accountRepository.deleteAll();
	}

	@Test
	void 이메일중복여부검사_성공__중복(){
		// when
		Boolean result = authService.checkDuplicateEmail(
			testMemberAccount.getEmail()
		);

		// then
		Assertions.assertTrue(result);
	}

	@Test
	void 계정로그인_성공(){
		// begin
		AccountRequest.Login loginDto = new AccountRequest.Login(
			testMemberAccount.getEmail(),
			AccountCreation.PASSWORD.getFirst()
		);

		// when
		Pair<String,String> resultToken = authService.LoginAccount(loginDto);
		String accessToken = resultToken.getFirst();
		String refreshToken = resultToken.getSecond();

		// then
		Assertions.assertEquals(
			testMemberAccount.getId(),
			jwtService.parseId(accessToken)
		);
		Assertions.assertEquals(
			testMemberAccount.getId(),
			jwtService.parseId(refreshToken)
		);
		Assertions.assertEquals(
			testMemberAccount.getEmail(),
			jwtService.getUserDetailFromToken(accessToken).getEmail()
		);
		Assertions.assertEquals(
			testMemberAccount.getRole(),
			jwtService.getUserDetailFromToken(accessToken).getRole()
		);
	}

	@Test
	void 계정_로그인_실패__다른_패스워드(){
		// begin
		AccountRequest.Login loginDto = new AccountRequest.Login(
			testMemberAccount.getEmail(),
			"incorrectPassword@!#@!#!"
		);

		// when
		assertThatThrownBy(
			()-> authService.LoginAccount(loginDto)
		)

		// then
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.FAIL_LOGIN.getMessage()
			);
	}

	@Test
	void 계정_로그인_실패__계정_삭제됨(){
		// begin
		testMemberAccount.delete();
		AccountRequest.Login loginDto = new AccountRequest.Login(
			testMemberAccount.getEmail(),
			AccountCreation.PASSWORD.getFirst()
		);

		// when
		assertThatThrownBy(
			()-> authService.LoginAccount(loginDto)
		)

			// then
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.REMOVED_ACCOUNT.getMessage()
			);
	}
}