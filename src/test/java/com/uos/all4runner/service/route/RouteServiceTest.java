package com.uos.all4runner.service.route;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.exception.CustomException;
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

	@Test
	void 임시경로상세조회_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();

		// when
		RouteResponse.TempDetails result = routeService
			.getTempRouteDetails(testMemberAccount.getId(),resultRouteId);

		// then
		Assertions.assertEquals(testMemberAccount.getName(),result.writerName());
		Assertions.assertEquals(RouteStatus.TEMP,result.routeStatus());
		Assertions.assertEquals(resultRouteId,result.routeId());
		Assertions.assertFalse(result.routeLinks().isEmpty());
	}

	@Test
	void 임시경로상세조회_실패__PUBLIC(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();
		routeService.switchRouteToPublic(testMemberAccount.getId(),resultRouteId);

		// when & then
		assertThatThrownBy(
			()->routeService.getTempRouteDetails(testMemberAccount.getId(),resultRouteId)
		)
			.isInstanceOf(CustomException.class)
			.hasMessageContaining(ErrorCode.ROUTE_STATUS_NOT_TEMP.getMessage());
	}

	@Test
	void 경로상세조회_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);
		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();
		routeService.updateTempRoute(
			updateRouteDto,
			testMemberAccount.getId(),
			resultRouteId
		);

		// when
		RouteResponse.Details result = routeService.getRouteDetails(
			testMemberAccount.getId(),
			resultRouteId
		);

		// then
		Assertions.assertEquals(testMemberAccount.getName(),result.writerName());
		Assertions.assertEquals(RouteStatus.PUBLIC,result.routeStatus());
		Assertions.assertEquals(resultRouteId,result.routeId());
		Assertions.assertFalse(result.routeLinks().isEmpty());
	}

	@Test
	void 본인경로검색_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation
			.createTestAuthentication(testMemberAccount.getId(), AccountRole.MEMBER);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		UUID resultRouteId = routeService
			.createOptimalRoute(optimalRouteDto, testMemberAccount.getId())
			.routeId();

		routeService.updateTempRoute(
			updateRouteDto,
			testMemberAccount.getId(),
			resultRouteId
		);

		// when
		Page<RouteResponse.Search> foundedRoutes = routeService.searchRoutes(
			testMemberAccount.getId(),
			testMemberAccount.getId(),
			PageRequest.of(0,10)
		);

		// then
		Assertions.assertFalse(foundedRoutes.getContent().isEmpty());
		Assertions.assertEquals(
			foundedRoutes.getContent().get(0).routeId(),
			resultRouteId
		);
	}
}