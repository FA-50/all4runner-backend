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
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "route")
public class Route extends BaseEntity {

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RouteStatus routeStatus;

	private String description;

	@Column(nullable = false)
	private Double estimatedKcal;

	@Column(nullable = false)
	private Double estimatedRunningTime;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "accountId",
		nullable = false,
		foreignKey = @ForeignKey(
			name = "fk_route_account",
			foreignKeyDefinition = "FOREIGN KEY(account_id) REFERENCES account(id) ON DELETE CASCADE"
		)
	)
	private Account account;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoryId", nullable = false)
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

	private Route(
		Account account,
		Category category
	){
		this.routeStatus = RouteStatus.TEMP;
		this.estimatedKcal = 0.0;
		this.estimatedRunningTime = 0.0;
		this.account = account;
		this.category = category;
	}

	public static Route createTemporaryRoute(
		Account account,
		Category category
	){
		Route route = new Route(
			account,
			category
		);
		account.mapToAccount(route);
		category.mapToCategory(route);
		return route;
	}

	public void updateTemporaryRoute(
		String description,
		Double estimatedKcal,
		Double estimatedRunningTime,
		Category category
	){
		this.description = description;
		this.routeStatus = RouteStatus.PUBLIC;
		this.estimatedKcal = estimatedKcal;
		this.estimatedRunningTime = estimatedRunningTime;
		this.category = category;
	}

	public void updatedDescription(String description){ this.description = description; }

	public void setPublicStatus(){ this.routeStatus = RouteStatus.PUBLIC; }

	public void setPrivateStatus(){ this.routeStatus = RouteStatus.PRIVATE; }

	public <T> void mapToRoute(T data){
		if (data instanceof RouteLink routeData){
			routeLinks.add(routeData);
		} else{
			reviews.add((Review)data);
		}
	}
}
