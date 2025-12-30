package com.uos.all4runner.domain.entity.route;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.CategoryCreation;

class RouteTest {

	Account account;
	Category category;

	@BeforeEach
	void setUp() {
		account = AccountCreation.createMember();
		category = CategoryCreation.createCategory();
	}

	@Test
	void 임시경로_생성_성공(){
		// when
		Route route = Route.createTemporaryRoute(
			account,
			category
		);

		// then
		Assertions.assertNotNull(route);
		Assertions.assertEquals(RouteStatus.TEMP,route.getRouteStatus());
		Assertions.assertEquals(account.getId(),route.getAccount().getId());
		Assertions.assertEquals(category.getId(),route.getCategory().getId());
	}

	@Test
	void 임시경로_변경_성공(){
		// begin
		Route route = Route.createTemporaryRoute(
			account,
			category
		);

		// when
		route.updateTemporaryRoute(
			"공개경로",
			14.5d,
			999d,
			CategoryCreation.createCategory()
		);

		// then
		Assertions.assertEquals(RouteStatus.PUBLIC, route.getRouteStatus());
	}

	@Test
	void 설명수정_성공(){
		// begin
		Route route = Route.createTemporaryRoute(
			account,
			category
		);

		// when
		route.updatedDescription("수정된 임시경로");

		// then
		Assertions.assertEquals("수정된 임시경로", route.getDescription());
	}

	@Test
	void 경로공개로변경_성공(){
		// begin
		Route route = Route.createTemporaryRoute(
			account,
			category
		);

		// when
		route.setPublicStatus();

		// then
		Assertions.assertEquals(RouteStatus.PUBLIC, route.getRouteStatus());
	}

	@Test
	void 경로비공개로변경_성공(){
		// begin
		Route route = Route.createTemporaryRoute(
			account,
			category
		);

		// when
		route.setPrivateStatus();

		// then
		Assertions.assertEquals(RouteStatus.PRIVATE, route.getRouteStatus());
	}
}