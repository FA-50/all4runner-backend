package com.uos.all4runner.service.account;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;

	@Override
	public void createMember(AccountRequest.Create request) {
		accountRepository.save(
			Account.createMember(
				request.name(),
				request.email(),
				request.password(),
				request.gender(),
				request.addressGu(),
				request.addressDong(),
				request.avgspeed(),
				request.weight()
			)
		);
	}

	@Override
	public void createAdmin(AccountRequest.Create request, UUID adminId) {
		checkSuperAdmin(adminId);
		accountRepository.save(
			Account.createAdmin(
				request.name(),
				request.email(),
				request.password(),
				request.gender(),
				request.addressGu(),
				request.addressDong(),
				request.avgspeed(),
				request.weight()
			)
		);
	}

	private void checkSuperAdmin(UUID userId){
		Account foundedAccount = accountRepository.findByIdOrThrow(userId);
		PreConditions.validate(
			foundedAccount.getRole().equals(AccountRole.SUPERADMIN),
			ErrorCode.NOT_ADMIN
		);
	}
}
