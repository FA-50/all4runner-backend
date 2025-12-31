package com.uos.all4runner.repository.review;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.dto.response.QReviewResponse_Search;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.review.QReview;
import com.uos.all4runner.domain.entity.route.Route;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QReview review = QReview.review;

	@Override
	public Page<ReviewResponse.Search> searchByRoute(
		UUID routeId,
		Pageable pageable
	) {

		List<ReviewResponse.Search> result = jpaQueryFactory
			.select(new QReviewResponse_Search(
				review.content,
				review.id,
				review.writedBy.name
			))
			.from(review)
			.where(review.route.id.eq(routeId))
			.orderBy(review.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		int total = jpaQueryFactory
			.select(review.id)
			.from(review)
			.where(review.route.id.eq(routeId))
			.fetch().size();

		return new PageImpl<>(result, pageable, total);
	}

}
