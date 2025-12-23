package com.uos.all4runner.service.account;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.exception.CustomException;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.util.AccountCreation;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AccountServiceTest {

	@Autowired
	AccountService accountService;
	@Autowired
	AccountRepository accountRepository;

	AccountRequest.Create accountDto;
	Account member;


	@BeforeEach
	void setUp() throws Exception {
		accountDto = new AccountRequest.Create(
			"이정수",
			"wjdtn747@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);

		member = AccountCreation.createMember();
		accountRepository.save(member);
	}
	@AfterEach
	void tearDown() throws Exception {
		accountRepository.deleteAll();
	}

	@Test
	void 멤버계정생성성공_성공(){
		accountService.createMember(accountDto);
		Account foundedMember = accountRepository.findByName("이정수").orElse(null);

		Assertions.assertNotNull(foundedMember);
		assertThat(foundedMember.getName()).isEqualTo("이정수");
		assertThat(foundedMember.getPassword()).isEqualTo("wjdu7471231");
	}

	@Test
	void 어드민계정생성_실패__어드민아님(){
		assertThrowsExactly(
			CustomException.class,
			() -> accountService.createAdmin(accountDto, member.getId())
		);
	}
}