package com.uos.all4runner.repository.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uos.all4runner.domain.dto.response.AccountResponse;

public interface AccountRepositoryCustom {
	Page<AccountResponse.Search> searchAccounts(String name, Pageable pageable);
}
