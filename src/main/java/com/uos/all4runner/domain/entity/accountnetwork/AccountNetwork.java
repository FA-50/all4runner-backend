package com.uos.all4runner.domain.entity.accountnetwork;

import java.util.UUID;

import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.network.Network;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accountnetwork")
public class AccountNetwork{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(nullable = false)
	private double linkKcal;

	@Column(nullable = false)
	private double linkCost;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "link_id")
	private Network network;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;
}
