package com.uos.all4runner.service.account;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;

public interface AccountService {
	boolean checkDuplicateEmail(String email);

	void createMember(AccountRequest.Create request);

	void createAdmin(AccountRequest.Create request);

	void deleteAccount(UUID currentId, UUID subjectId);

	void deleteAccountPermanently(UUID subjectId);

	void restoreAccount(UUID subjectId);

	void updateAccount(UUID currentId, UUID subjectId, AccountRequest.Update request);

	void updatePassword(UUID currentId, AccountRequest.UpdatePassword request);

	void updatePasswordByAdmin(UUID subjectId, String newPassword);

	AccountResponse.Details getAccountDetails(UUID currentId, UUID subjectId);

	Page<AccountResponse.Search> searchAccounts(String name, Pageable pageable);
}
