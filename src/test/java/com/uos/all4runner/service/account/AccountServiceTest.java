package com.uos.all4runner.service.account;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
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
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.exception.CustomException;
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
		Assertions.assertTrue(
			passwordEncoder.matches(
				AccountCreation.PASSWORD.getFirst(),
				foundedMember.getPassword()
			)
		);
	}

	@Test
	void 어드민계정생성_성공_슈퍼어드민(){
		// begin
		TestingAuthenticationToken testAuth_SUPERADMIN = AuthenticationCreation
			.createTestAuthentication(UUID.randomUUID(), AccountRole.SUPERADMIN);
		SecurityContextHolder.getContext().setAuthentication(testAuth_SUPERADMIN);
		// when
		accountService.createAdmin(accountDto);
		// then
		Account foundedAccount = accountRepository.findByName("멤버테스터").orElse(null);
		Assertions.assertNotNull(foundedAccount);
		Assertions.assertEquals(AccountRole.ADMIN, foundedAccount.getRole());
	}

	@ParameterizedTest
	@EnumSource(
		value = AccountRole.class,
		names = {"ADMIN", "MEMBER"},
		mode =  EnumSource.Mode.INCLUDE
	)
	void 어드민계정생성_실패__멤버_어드민(AccountRole accountRole){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(UUID.randomUUID(), accountRole);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when & then
		assertThrowsExactly(
			AuthorizationDeniedException.class,
			() -> accountService.createAdmin(accountDto)
		);
	}

	@Test
	void 계정삭제_성공_멤버(){
		// begin
		Account member = AccountCreation.createMember();
		accountRepository.save(member);
		TestingAuthenticationToken testAuth_MEMBER = AuthenticationCreation
			.createTestAuthentication(member.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth_MEMBER);

		// when
		accountService.deleteAccount(member.getId(),member.getId());

		// then
		Assertions.assertTrue(member.getStatus().equals(AccountStatus.REMOVED));
	}

	@Test
	void 계정삭제_성공_어드민(){
		// begin
		Account subjectMember = AccountCreation.createMember();
		accountRepository.save(subjectMember);
		Account admin = AccountCreation.createAdmin();
		accountRepository.save(admin);
		TestingAuthenticationToken testAuth_ADMIN  = AuthenticationCreation
			.createTestAuthentication(admin.getId(), AccountRole.ADMIN);
		SecurityContextHolder.getContext().setAuthentication(testAuth_ADMIN);

		// when
		accountService.deleteAccount(admin.getId(),subjectMember.getId());

		// then
		Assertions.assertTrue(subjectMember.getStatus().equals(AccountStatus.REMOVED));
	}

	@Test
	void 계정삭제_실패__본인계정아님(){
		// begin
		Account member = AccountCreation.createMember();
		Account subjectMember = AccountCreation.createMember();
		accountRepository.save(member);
		accountRepository.save(subjectMember);

		TestingAuthenticationToken testAuth_MEMBER = AuthenticationCreation
			.createTestAuthentication(member.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth_MEMBER);

		// when & then
		assertThatThrownBy(
			()-> accountService.deleteAccount(member.getId(),subjectMember.getId())
		)
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.ACCESS_NOT_ALLOWED.getMessage());
	}

	@Test
	void 계정영구삭제_성공(){
		// begin
		Account subjectMember = AccountCreation.createMember();
		subjectMember.delete();
		accountRepository.save(subjectMember);
		TestingAuthenticationToken testAuth_ADMIN  = AuthenticationCreation
			.createTestAuthentication(UUID.randomUUID(), AccountRole.ADMIN);
		SecurityContextHolder.getContext().setAuthentication(testAuth_ADMIN);

		// when
		accountService.deleteAccountPermanently(subjectMember.getId());

		// then
		Account foundedMember = accountRepository.findById(subjectMember.getId())
			.orElse(null);

		Assertions.assertNull(foundedMember);
	}

	@Test
	void 계정영구삭제_실패__계정_REMOVED아님(){
		// begin
		Account subjectMember = AccountCreation.createMember();
		accountRepository.save(subjectMember);
		TestingAuthenticationToken testAuth_ADMIN  = AuthenticationCreation
			.createTestAuthentication(UUID.randomUUID(), AccountRole.ADMIN);
		SecurityContextHolder.getContext().setAuthentication(testAuth_ADMIN);

		// when & then
		assertThatThrownBy(
			()-> accountService.deleteAccountPermanently(subjectMember.getId())
		)
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.NOT_REMOVED.getMessage());
	}

	@Test
	void 계정영구삭제_실패__MEMBER(){
		// begin
		Account subjectMember = AccountCreation.createMember();
		accountRepository.save(subjectMember);
		TestingAuthenticationToken testAuth_MEMBER  = AuthenticationCreation
			.createTestAuthentication(UUID.randomUUID(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth_MEMBER);

		// when & then
		assertThatThrownBy(
			()-> accountService.deleteAccountPermanently(subjectMember.getId())
		)
			.isInstanceOf(AuthorizationDeniedException.class);
	}
}