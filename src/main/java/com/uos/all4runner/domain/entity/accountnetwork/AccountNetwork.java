package com.uos.all4runner.domain.entity.accountnetwork;


import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.network.Network;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accountnetwork")
public class AccountNetwork extends BaseEntity {

	@Column(nullable = false)
	private double linkKcal;

	@Column(nullable = false)
	private double linkCost;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "link_id")
	private Network network;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;
}
