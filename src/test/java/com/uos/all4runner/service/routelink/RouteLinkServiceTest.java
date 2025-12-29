package com.uos.all4runner.service.routelink;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.exception.CustomException;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.repository.routelink.RouteLinkRepository;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.CategoryCreation;
import com.uos.all4runner.util.RouteCreation;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RouteLinkServiceTest {

	@Autowired
	RouteLinkService routeLinkService;
	@Autowired
	RouteLinkRepository routeLinkRepository;
	@Autowired
	RouteRepository routeRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CategoryRepository categoryRepository;


	static final String nodeIdSet = "391472,341760,393566,398348,348740";
	Route route;

	@BeforeEach
	void setUp() {
		route = RouteCreation.createTempRoute(
			accountRepository.save(AccountCreation.createMember()),
			categoryRepository.save(CategoryCreation.createCategory())
		);
		routeRepository.save(route);
	}

	@Test
	void 최단거리경로_생성_성공(){
		// when
		routeLinkService.createShortestPaths(
			nodeIdSet,
			5000,
			LinkType.CROSSWALK,
			route.getId()
		);

		// then
		List<RouteLink> routeLinks = routeLinkRepository.findAll();
		Assertions.assertFalse(routeLinks.isEmpty());
		Assertions.assertEquals(route.getId(), routeLinks.get(0).getRoute().getId());
	}

	@Test
	void 최단거리경로_생성__실패_노드부족(){
		// begin
		String tempNodeIdSet = "391472,";

		// when & then
		assertThatThrownBy(
			() -> routeLinkService.createShortestPaths(
				tempNodeIdSet,
				5000,
				LinkType.CROSSWALK,
				route.getId()
			)
		)
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.NODE_NOT_INCLUDE.getMessage());
	}

	@Test
	void 최적거리경로_생성_성공(){
		// when
		routeLinkService.createOptimalPaths(
			nodeIdSet,
			5000,
			1,
			LinkType.CROSSWALK,
			route.getId()
		);

		// then
		List<RouteLink> routeLinks = routeLinkRepository.findAll();
		Assertions.assertFalse(routeLinks.isEmpty());
		Assertions.assertEquals(route.getId(), routeLinks.get(0).getRoute().getId());
	}
}