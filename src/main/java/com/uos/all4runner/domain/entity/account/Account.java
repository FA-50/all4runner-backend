package com.uos.all4runner.domain.entity.account;

import com.uos.all4runner.common.BaseEntity;
import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.dto.request.AccountRequest;
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
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
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
	private String addressGu;

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

	public Account(
		String name,
		String email,
		String password,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgSpeed,
		int weight,
		AccountRole role
	){
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.addressGu = addressGu;
		this.addressDong = addressDong;
		this.avgSpeed = avgSpeed;
		this.weight = weight;
		this.role = role;
	}

	public static Account createMember(
		AccountRequest.Create request
	){
		return new Account(
			request.name(),
			request.email(),
			request.password(),
			request.gender(),
			request.addressGu(),
			request.addressDong(),
			request.avgspeed(),
			request.weight(),
			AccountRole.MEMBER
		);
	}

	public static Account createAdmin(
		AccountRequest.Create request
	){
		return new Account(
			request.name(),
			request.email(),
			request.password(),
			request.gender(),
			request.addressGu(),
			request.addressDong(),
			request.avgspeed(),
			request.weight(),
			AccountRole.ADMIN
		);
	}
}
