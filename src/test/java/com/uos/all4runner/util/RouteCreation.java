package com.uos.all4runner.util;

import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;

public class RouteCreation {
	public static Route createTempRoute(
		Account account,
		Category category
	) {
		return Route.createTemporaryRoute(
			account,
			category
		);
	}
}
