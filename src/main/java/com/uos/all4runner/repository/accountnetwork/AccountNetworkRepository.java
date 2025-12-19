package com.uos.all4runner.repository.accountnetwork;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.accountnetwork.AccountNetwork;

@Repository
public interface AccountNetworkRepository extends JpaRepository<AccountNetwork, UUID> {
}
