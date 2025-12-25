package com.uos.all4runner.service.account;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void createMember(AccountRequest.Create request) {
		accountRepository.save(
			Account.createMember(
				request.name(),
				request.email(),
				passwordEncoder.encode(request.password()),
				request.gender(),
				request.addressGu(),
				request.addressDong(),
				request.avgspeed(),
				request.weight()
			)
		);
	}

	@Override
	@PreAuthorize("hasRole('SUPERADMIN')")
	public void createAdmin(AccountRequest.Create request) {
		accountRepository.save(
			Account.createAdmin(
				request.name(),
				request.email(),
				passwordEncoder.encode(request.password()),
				request.gender(),
				request.addressGu(),
				request.addressDong(),
				request.avgspeed(),
				request.weight()
			)
		);
	}

	@Override
	@PreAuthorize("#currentId == authentication.principal.id")
	public void deleteAccount(UUID currentId, UUID subjectId) {
		checkModifyPermission(currentId, subjectId);

		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);

		PreConditions.validate(
			!subjectAccount.getRole().equals(AccountRole.SUPERADMIN),
			ErrorCode.CANNOT_MODIFY_SUPERADMIN
		);

		subjectAccount.delete();
	}

	@Override
	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	public void deleteAccountPermanently(UUID subjectId) {
		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);

		PreConditions.validate(
			!subjectAccount.getRole().equals(AccountRole.SUPERADMIN),
			ErrorCode.CANNOT_MODIFY_SUPERADMIN
		);

		PreConditions.validate(
			subjectAccount.getStatus().equals(AccountStatus.REMOVED),
			ErrorCode.NOT_REMOVED
		);

		accountRepository.deleteById(subjectId);
	}

	@Override
	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	public void restoreAccount(UUID subjectId) {
		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);

		PreConditions.validate(
			subjectAccount.getStatus().equals(AccountStatus.REMOVED),
			ErrorCode.NOT_REMOVED
		);

		subjectAccount.restore();
	}

	@Override
	@PreAuthorize("#currentId == authentication.principal.id")
	public void updateAccount(UUID currentId, UUID subjectId, AccountRequest.Update request) {
		checkModifyPermission(currentId, subjectId);

		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);
		subjectAccount.update(
			(request.name() != null)? request.name() : subjectAccount.getName(),
			(request.gender() != null)? request.gender() : subjectAccount.getGender(),
			(request.addressGu() != null)? request.addressGu() : subjectAccount.getAddressGu(),
			(request.addressDong() != null)? request.addressDong() : subjectAccount.getAddressDong(),
			(request.avgspeed() != null)? request.avgspeed() : subjectAccount.getAvgSpeed(),
			(request.weight() != null)? request.weight() : subjectAccount.getWeight()
		);
	}

	@Override
	@PreAuthorize("#currentId == authentication.principal.id")
	public void updatePassword(UUID currentId, AccountRequest.UpdatePassword request) {
		Account foundedAccount = accountRepository.findByIdOrThrow(currentId);

		PreConditions.validate(
			passwordEncoder.matches(
				request.currentPassword(),
				foundedAccount.getPassword()
			),
			ErrorCode.INVALID_PASSWORD
		);

		PreConditions.validate(
			request.currentPassword().equals(request.newPassword()),
			ErrorCode.SAME_PASSWORD
		);

		foundedAccount.updatePassword(passwordEncoder.encode(request.newPassword()));
	}

	@Override
	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	public void updatePasswordByAdmin(UUID subjectId, String newPassword) {
		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);

		PreConditions.validate(
			!subjectAccount.getRole().equals(AccountRole.SUPERADMIN),
			ErrorCode.CANNOT_MODIFY_SUPERADMIN
		);

		subjectAccount.updatePassword(passwordEncoder.encode(newPassword));
	}

	@Override
	@PreAuthorize("#currentId == authentication.principal.id")
	public AccountResponse.Details getAccountDetails(UUID currentId, UUID subjectId) {
			checkReadPermission(currentId, subjectId);
		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);
		return new AccountResponse.Details(
			subjectAccount.getId(),
			subjectAccount.getName(),
			subjectAccount.getEmail(),
			subjectAccount.getGender(),
			subjectAccount.getAddressGu(),
			subjectAccount.getAddressDong(),
			subjectAccount.getAvgSpeed(),
			subjectAccount.getWeight()
		);
	}

	@Override
	@PreAuthorize("hasRole({'ADMIN', 'SUPERADMIN'})")
	public Page<AccountResponse.Search> searchAccounts(String name, Pageable pageable) {
		return accountRepository.searchAccounts(name, pageable);
	}

	private void checkReadPermission(UUID currentId, UUID subjectId) {
		if (!currentId.equals(subjectId)) {
			Account currentAccount = accountRepository.findByIdOrThrow(currentId);
			PreConditions.validate(
				!currentAccount.getRole().equals(AccountRole.MEMBER),
				ErrorCode.ACCOUNT_ACCESS_NOT_ALLOWED
			);
		}
	}

	private void checkModifyPermission(UUID currentId, UUID subjectId) {
		Account subjectAccount = accountRepository.findByIdOrThrow(subjectId);
		if (subjectAccount.getRole() == AccountRole.ADMIN ||
			subjectAccount.getRole() == AccountRole.SUPERADMIN) {
			PreConditions.validate(
				currentId.equals(subjectId),
				ErrorCode.ACCOUNT_ACCESS_NOT_ALLOWED
			);
		} else {
			Account currentAccount = accountRepository.findByIdOrThrow(currentId);
			PreConditions.validate(
				currentAccount.getRole().equals(AccountRole.ADMIN) |
					currentAccount.getRole().equals(AccountRole.SUPERADMIN) |
					currentId.equals(subjectId),
				ErrorCode.ACCOUNT_ACCESS_NOT_ALLOWED
			);
		}
	}
}
