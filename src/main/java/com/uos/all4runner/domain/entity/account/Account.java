package com.uos.all4runner.domain.entity.account;

import com.uos.all4runner.common.BaseEntity;
import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.entity.accountnetwork.AccountNetwork;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private String addrssGu;

	@Column(nullable = false)
	private String addressDong;

	@Column(nullable = false)
	private Double avgSpeed;

	@Column(nullable = false)
	private int weight;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountRole role;

	@OneToOne(
		mappedBy = "account",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private AccountNetwork accountNetwork;
}
