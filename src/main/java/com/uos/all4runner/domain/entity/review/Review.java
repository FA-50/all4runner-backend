package com.uos.all4runner.domain.entity.review;


import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Review extends BaseEntity {
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "writed_by", nullable = false)
	private Account writedBy;

	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;

	public Review(
		String content,
		Account writedBy,
		Route route
	) {
		this.content = content;
		this.writedBy = writedBy;
		this.route = route;
	}

	public static Review createReview(
		String content,
		Account account,
		Route route
	) {
		Review review = new Review(
			content,
			account,
			route
		);
		account.mapToAccount(review);
		route.mapToRoute(review);
		return review;
	}

	public void updateReview(String content){
		this.content = content;
	}
}
