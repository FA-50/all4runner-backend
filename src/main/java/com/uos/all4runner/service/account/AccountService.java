package com.uos.all4runner.service.account;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;

public interface AccountService {

	void createMember(AccountRequest.Create request);

	@PreAuthorize("hasRole('SUPERADMIN')")
	void createAdmin(AccountRequest.Create request);

	@PreAuthorize("#currentId == authentication.principal.id")
	void deleteAccount(UUID currentId, UUID subjectId);

	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	void deleteAccountPermanently(UUID subjectId);

	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	void restoreAccount(UUID subjectId);

	@PreAuthorize("#currentId == authentication.principal.id")
	void updateAccount(UUID currentId, UUID subjectId, AccountRequest.Update request);

	@PreAuthorize("#currentId == authentication.principal.id")
	void updatePassword(UUID currentId, AccountRequest.UpdatePassword request);

	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	void updatePasswordByAdmin(UUID subjectId, String newPassword);

	@PreAuthorize("#currentId == authentication.principal.id")
	AccountResponse.Details getAccountDetails(UUID currentId, UUID subjectId);

	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	Page<AccountResponse.Search> searchAccounts(String name, Pageable pageable);
}
