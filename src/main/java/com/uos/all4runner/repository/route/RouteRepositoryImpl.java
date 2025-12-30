package com.uos.all4runner.repository.route;

import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.dto.response.QRouteResponse_Search;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.domain.entity.account.QAccount;
import com.uos.all4runner.domain.entity.route.QRoute;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private final QRoute qRoute = QRoute.route;
	private final QAccount qAccount = QAccount.account;

	@Override
	public Page<RouteResponse.Search> searchRoutesByAccountId(
		UUID accountId,
		Pageable pageable
	) {
		BooleanBuilder booleanBuilder = new BooleanBuilder()
			.and(qAccount.id.eq(accountId));

		List<RouteResponse.Search> routes = jpaQueryFactory
			.select(new QRouteResponse_Search(
				qRoute.id,
				qRoute.description,
				qRoute.category.name
			))
			.from(qAccount)
			.join(qRoute).on(qRoute.account.id.eq(qAccount.id))
			.where(booleanBuilder)
			.orderBy(qRoute.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		int total = jpaQueryFactory
			.select(qRoute.id)
			.from(qAccount)
			.join(qRoute).on(qRoute.account.id.eq(qAccount.id))
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(routes, pageable, total);
	}

	@Override
	public Page<RouteResponse.Search> searchPublicRoutes(
		String categoryName,
		Pageable pageable
	) {
		BooleanBuilder  booleanBuilder = new BooleanBuilder()
			.and(containCategoryName(categoryName))
			.and(qRoute.routeStatus.eq(RouteStatus.PUBLIC));

		List<RouteResponse.Search> results = jpaQueryFactory
			.select(new QRouteResponse_Search(
				qRoute.id,
				qRoute.description,
				qRoute.category.name
			))
			.from(qRoute)
			.where(booleanBuilder)
			.orderBy(qRoute.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		int total = jpaQueryFactory
			.select(qRoute.id)
			.from(qRoute)
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(results, pageable, total);
	}

	public BooleanExpression containCategoryName(String categoryName){
		return 	(Strings.isNotBlank(categoryName))?
			qRoute.category.name.containsIgnoreCase(categoryName):null;
	}
}
