package com.uos.all4runner.repository.network;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.network.Network;

@Repository
public interface NetworkRepository extends JpaRepository<Network, UUID> {
}
