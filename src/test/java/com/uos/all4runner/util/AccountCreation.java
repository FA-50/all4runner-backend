package com.uos.all4runner.util;

import java.util.UUID;

import org.springframework.security.authentication.TestingAuthenticationToken;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.security.DefaultCurrentUser;

public class AccountCreation {
	public static Account createMember(){
		return Account.createMember(
			"멤버테스터",
			"member@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);
	}

	public static Account createAdmin(){
		return Account.createAdmin(
			"어드민테스터",
			"admin@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);
	}

	public static AccountRequest.Create createAccountCreateRequest() {
		return new AccountRequest.Create(
			"멤버테스터",
			"member@naver.com",
			"wjdu7471231",
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);
	}


}
