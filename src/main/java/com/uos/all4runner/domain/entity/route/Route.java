package com.uos.all4runner.domain.entity.route;

import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.review.Review;
import com.uos.all4runner.domain.entity.routelink.RouteLink;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
public class Route extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RouteStatus routeStatus;

	private String description;

	@Column(nullable = false)
	private Double estimatedKcal;

	@Column(nullable = false)
	private Double estimatedTime;

	@ManyToOne(optional = false)
	@JoinColumn(name = "accountId")
	private Account account;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(
		mappedBy = "route",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Review> reviews = new ArrayList<Review>();

	@OneToMany(
		mappedBy = "route",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<RouteLink> routeLinks = new ArrayList<RouteLink>();
}
