package com.uos.all4runner.service.route;

import java.util.UUID;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.service.routelink.RouteLinkService;
import com.uos.all4runner.util.PreConditions;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteServiceImpl implements RouteService {

	private final RouteLinkService routeLinkService;
	private final AccountRepository accountRepository;
	private final CategoryRepository categoryRepository;
	private final RouteRepository routeRepository;

	@Override
	public void createShortestRoute(
		RouteRequest.ShortRoute request,
		UUID accountId
	) {
		Route tempRoute = createTempRoute(accountId);

		routeLinkService.createShortestPaths(
			request.nodeIdSet(),
			request.maxDistance(),
			request.excludeType(),
			tempRoute.getId()
		);
	}

	@Override
	public void createOptimalRoute(
		RouteRequest.OptimalRoute request,
		UUID accountId
	) {
		Route tempRoute = createTempRoute(accountId);

		routeLinkService.createOptimalPaths(
			request.nodeIdSet(),
			request.maxDistance(),
			request.slopeConstraints(),
			request.excludeType(),
			tempRoute.getId()
		);
	}

	@Override
	public void updateTempRoute(
		RouteRequest.TempToPublic request,
		UUID accountId,
		UUID routeId
	){
		Route foundedRoute = routeRepository.findByIdOrThrow(routeId);

		checkReadPermission(accountId, foundedRoute.getAccount().getId());

		PreConditions.validate(
			foundedRoute.getRouteStatus().equals(RouteStatus.TEMP),
			ErrorCode.ROUTE_STATUS_NOT_TEMP
		);

		Account account = accountRepository.findByIdOrThrow(accountId);

		Pair<Double,Double> kcalAndTime = calculateKcalAndTime(
			foundedRoute.getRouteLinks(),
			account.getWeight(),
			account.getAvgSpeed()
		);

		foundedRoute.updateTemporaryRoute(
			request.description(),
			kcalAndTime.getFirst(),
			kcalAndTime.getSecond(),
			categoryRepository.findByNameOrThrow(request.categoryName())
		);
	}



	private void checkReadPermission(UUID accountId, UUID subjectId) {
		if (!accountId.equals(subjectId)) {
			Account currentAccount = accountRepository.findByIdOrThrow(accountId);
			PreConditions.validate(
				!currentAccount.getRole().equals(AccountRole.MEMBER),
				ErrorCode.ACCESS_NOT_ALLOWED
			);
		}
	}

	private Pair<Double,Double> calculateKcalAndTime(
		List<RouteLink> routeLinks,
		double weight,
		double avgSpeed
		) {
		double speed_m_min = avgSpeed * (1000d/60d);

		double totalKcal = 0;
		double totalTime = 0;
		for (RouteLink routeLink : routeLinks) {
			double v0;
			double linkTime = routeLink.getLinkLength()/speed_m_min;
			double slope = routeLink.getLinkSlope();

			if (slope > 0){
				v0 = ( (3.5 + ( 0.2 * speed_m_min ) + ( 0.9 * speed_m_min * ( slope / 100d ) ) ) * weight ) /1000;
			} else {
				v0 = ( ( 3.5 + ( 0.2 * speed_m_min ) ) * weight )/1000;
			}
			totalKcal += v0 * 5 * linkTime;

			totalTime += linkTime;
		}

		return Pair.of(totalKcal, totalTime);
	}

	private Route createTempRoute(UUID accountId){
		return routeRepository.save(
			Route.createTemporaryRoute(
				accountRepository.findByIdOrThrow(accountId),
				categoryRepository.findByNameOrThrow(Category.UNASSIGNED)
			)
		);
	}
}
