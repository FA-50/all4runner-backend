package com.uos.all4runner.domain.entity.accountnetwork;


import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.network.Network;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accountnetwork")
public class AccountNetwork {

	@Id
	@UuidGenerator
	@Column(name = "id", nullable = false, updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected UUID id;

	@Column(nullable = false)
	private double linkKcal;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "link_id")
	private Network network;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;
}
