package com.uos.all4runner.domain.entity.review;

import java.util.UUID;

import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Review extends BaseEntity {
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(
		name = "writed_by",
		nullable = false,
		foreignKey = @ForeignKey(
			name = "fk_review_account",
			foreignKeyDefinition = "FOREIGN KEY(writed_by) REFERENCES account(id) ON DELETE CASCADE"
		)
	)
	private Account writedBy;

	@ManyToOne
	@JoinColumn(
		name = "route_id",
		nullable = false,
		foreignKey = @ForeignKey(
			name = "fk_review_route",
			foreignKeyDefinition = "FOREIGN KEY(route_id) REFERENCES route(id) ON DELETE CASCADE"
		)
	)
	private Route route;
}
