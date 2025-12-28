package com.uos.all4runner.util;

import org.springframework.data.util.Pair;

import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;

public class AccountCreation {

	public static final Pair<String,String> PASSWORD = Pair.of(
		"wjdtn@@!!@",
		"$2a$10$m/BzNm4PYtv7MrxuSzNg5.DYxPIpOCSB4LSh4JbrdA4HRDmgwJdwi"
	);

	public static Account createMember(){
		return Account.createMember(
			"멤버테스터",
			"member@naver.com",
			PASSWORD.getSecond(),
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
			PASSWORD.getSecond(),
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
			PASSWORD.getSecond(),
			Gender.MALE,
			"동대문구",
			"휘경동",
			14.5,
			80D
		);
	}
}
