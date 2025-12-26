package com.uos.all4runner.domain.entity.account;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.util.AccountCreation;

@ActiveProfiles("test")
class AccountTest {

	@Test
	void 멤버계정_생성_성공() {
		// when
		Account account = Account.createMember(
			"멤버테스터",
			"member@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);

		// then
		Assertions.assertNotNull(account);
		Assertions.assertEquals(AccountRole.MEMBER, account.getRole());
		Assertions.assertEquals("멤버테스터", account.getName());
	}

	@Test
	void 어드민계정_생성_성공(){
		// when
		Account account = Account.createAdmin(
			"어드민테스터",
			"admin@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);

		// then
		Assertions.assertNotNull(account);
		Assertions.assertEquals(AccountRole.ADMIN, account.getRole());
		Assertions.assertEquals("어드민테스터", account.getName());
	}

	@Test
	void 계정_삭제_성공__Soft_Delete(){
		// begin
		Account account = AccountCreation.createMember();

		// when
		account.delete();

		// then
		Assertions.assertEquals(AccountStatus.REMOVED, account.getStatus());
	}

	@Test
	void 계정_복구_성공(){
		// begin
		Account account = AccountCreation.createMember();
		account.delete();

		// when
		account.restore();

		// then
		Assertions.assertEquals(AccountStatus.ACTIVATED, account.getStatus());
	}

	@Test
	void 계정_수정_성공(){
		// begin
		Account account = AccountCreation.createMember();

		// when
		account.update(
			"이정수",
			Gender.FEMALE,
			"구구",
			"동동",
			14.5D,
			80D
		);

		// then
		Assertions.assertEquals(Gender.FEMALE, account.getGender());
		Assertions.assertEquals(14.5D,account.getAvgSpeed());
	}
}