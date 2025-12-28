package com.uos.all4runner.repository.routelink;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.CategoryCreation;
import com.uos.all4runner.util.RouteCreation;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RouteLinkRepositoryTest {
	@Autowired
	RouteLinkRepository routeLinkRepository;
	@Autowired
	RouteRepository routeRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CategoryRepository categoryRepository;

	static final Long[] NODE_SET = {
		391472l, 341760l, 393566l, 398348l, 348740l
	};

	static final String dijkstraSql = """
		select
				cast(id as bigint) as id,
				cast(fnode as bigint) as source,
				cast(tnode as bigint) as target,
				link_cost as cost
				from linknetwork
		""";

	Route route;

	@BeforeEach
	void setUp() throws Exception {
		route = RouteCreation.createTempRoute(
			accountRepository.save(AccountCreation.createMember()),
			categoryRepository.save(CategoryCreation.createCategory())
		);

		routeRepository.save(route);
	}

	@Test
	void 최적경로_생성__성공(){
		// when
		routeLinkRepository
			.createShortestPaths(
				NODE_SET,
				5000,
				dijkstraSql,
				route.getId()
			);

		// then
		List<RouteLink> routeLinks = routeLinkRepository.findAll();
		Assertions.assertFalse(routeLinks.isEmpty());
		Assertions.assertEquals(route.getId(), routeLinks.get(0).getRoute().getId());
	}
}