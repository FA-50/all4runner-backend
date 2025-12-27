package com.uos.all4runner.domain.entity.account;

import java.util.ArrayList;

import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.Gender;
import com.uos.all4runner.domain.entity.review.Review;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

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
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private String addressGu;

	@Column(nullable = false)
	private String addressDong;

	@Column(nullable = false)
	private Double avgSpeed;

	@Column(nullable = false)
	private Double weight;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountRole role;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@OneToMany(
		mappedBy = "account",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Route> routes = new ArrayList<>();

	@OneToMany(
		mappedBy = "account",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Review> reviews = new ArrayList<>();

	private Account(
		String name,
		String email,
		String password,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgSpeed,
		Double weight,
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
		this.status = AccountStatus.ACTIVATED;
	}

	public static Account createMember(
		String name,
		String email,
		String password,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgspeed,
		Double weight
	){
		return new Account(
			name,
			email,
			password,
			gender,
			addressGu,
			addressDong,
			avgspeed,
			weight,
			AccountRole.MEMBER
		);
	}

	public static Account createAdmin(
		String name,
		String email,
		String password,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgspeed,
		Double weight
	){
		return new Account(
			name,
			email,
			password,
			gender,
			addressGu,
			addressDong,
			avgspeed,
			weight,
			AccountRole.ADMIN
		);
	}

	public void delete(){
		this.status = AccountStatus.REMOVED;
	}

	public void restore(){
		this.status = AccountStatus.ACTIVATED;
	}

	public void update(
		String name,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgSpeed,
		Double weight
	){
		this.name = name;
		this.gender = gender;
		this.addressGu = addressGu;
		this.addressDong = addressDong;
		this.avgSpeed = avgSpeed;
		this.weight = weight;
	}

	public void updatePassword(String password){
		this.password = password;
	}
}
