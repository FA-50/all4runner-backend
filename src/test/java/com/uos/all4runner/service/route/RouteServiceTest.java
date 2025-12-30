package com.uos.all4runner.service.route;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.repository.routelink.RouteLinkRepository;
import com.uos.all4runner.util.AccountCreation;
import com.uos.all4runner.util.AuthenticationCreation;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RouteServiceTest {
	
	@Autowired
	RouteService routeService;
	@Autowired
	RouteRepository routeRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	RouteLinkRepository routeLinkRepository;

	static final String nodeIdSet = "391472,341760,393566,398348,348740";
	RouteRequest.ShortRoute shortRouteDto;
	RouteRequest.OptimalRoute optimalRouteDto;
	RouteRequest.TempToPublic updateRouteDto;
	Account testAdminAccount;
	Account testMemberAccount;


	@BeforeEach
	void setUp(){
		testAdminAccount = AccountCreation.createAdmin();
		testMemberAccount = AccountCreation.createMember();
		accountRepository.save(testAdminAccount);
		accountRepository.save(testMemberAccount);

		shortRouteDto = new RouteRequest.ShortRoute(
			nodeIdSet,
			5000,
			LinkType.FOOTPATH
		);
		
		optimalRouteDto = new RouteRequest.OptimalRoute(
			nodeIdSet,
			5000,
			1,
			LinkType.FOOTPATH
		);

		updateRouteDto = new RouteRequest.TempToPublic(
			"경로설명",
			Category.UNASSIGNED
		);
	}
	
	@Test
	void 최단경로생성_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		UUID resultRouteId = routeService
			.createShortestRoute(shortRouteDto, testMemberAccount.getId())
			.routeId();

		// then
		Route foundedRoute = routeRepository.findByIdOrThrow(resultRouteId);
		List<RouteLink> foundedLinks = foundedRoute.getRouteLinks();
		Assertions.assertFalse(foundedLinks.isEmpty());
	}

	@Test
	void 최적경로생성_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();

		// then
		Route foundedRoute = routeRepository.findByIdOrThrow(resultRouteId);
		List<RouteLink> foundedLinks = foundedRoute.getRouteLinks();
		Assertions.assertFalse(foundedLinks.isEmpty());
	}

	@Test
	void 임시경로공개전환_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();

		// when
		routeService.updateTempRoute(
			updateRouteDto,
			testMemberAccount.getId(),
			resultRouteId
		);

		// then
		Route foundedRoute = routeRepository.findByIdOrThrow(resultRouteId);
		Assertions.assertEquals(RouteStatus.PUBLIC, foundedRoute.getRouteStatus());
		Assertions.assertTrue(foundedRoute.getEstimatedKcal() > 0);
		Assertions.assertTrue(foundedRoute.getEstimatedRunningTime() > 0);
	}

	@Test
	void 임시경로삭제_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();

		// when
		routeService.deleteTempByAccountId(testMemberAccount.getId());

		// then
		Assertions.assertNull(routeRepository.findById(resultRouteId).orElse(null));
		List<RouteLink> foundedRouteLinks = routeLinkRepository.findAllByRouteId(resultRouteId);
		Assertions.assertTrue(foundedRouteLinks.isEmpty());
	}
}