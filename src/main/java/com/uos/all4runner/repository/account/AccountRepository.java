package com.uos.all4runner.repository.account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.exception.CustomException;

@Repository
public interface AccountRepository extends JpaRepository<Account,UUID> {
	default Account findByIdOrThrow(UUID accountId){
		return findById(accountId).orElseThrow(
			() -> new CustomException(ErrorCode.FAIL_LOGIN)
		);
	}
	Optional<Account> findByName(String name);
}
