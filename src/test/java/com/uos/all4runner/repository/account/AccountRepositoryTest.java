package com.uos.all4runner.repository.account;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.util.AccountCreation;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	Account testAccount;

	@BeforeEach
	void setUp() throws Exception {
		testAccount = AccountCreation.createMember();
		accountRepository.save(testAccount);
	}

	@Test
	void 계정검색_성공(){
		// when
		Page<AccountResponse.Search> results = accountRepository.searchAccounts(
			testAccount.getName(),
			PageRequest.of(0,10)
		);

		// then
		Assertions.assertFalse(results.getContent().isEmpty());
		Assertions.assertEquals(
			testAccount.getId(),
			results.getContent().get(0).accountId()
		);
	}
}