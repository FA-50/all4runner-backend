package com.uos.all4runner.repository.account;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,UUID> {
}
