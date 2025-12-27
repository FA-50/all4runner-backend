package com.uos.all4runner.domain.entity.review;

import java.util.UUID;

import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Review extends BaseEntity {
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "writer_id", nullable = false)
	private Account account;

	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;
}
