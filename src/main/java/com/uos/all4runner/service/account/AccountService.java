package com.uos.all4runner.service.account;

import java.util.UUID;

import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;

public interface AccountService {
	void createMember(AccountRequest.Create request);

	void createAdmin(AccountRequest.Create request, UUID adminId);
}
