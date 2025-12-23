package com.uos.all4runner.domain.entity.review;

import java.util.UUID;

import com.uos.all4runner.common.BaseEntity;
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

	@Column(nullable = false)
	private UUID writerId;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;
}
