package com.uos.all4runner.service.route;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.entity.account.Account;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.repository.route.RouteRepository;
import com.uos.all4runner.service.routelink.RouteLinkService;

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
	public void createTemporaryShortestRoute(
		RouteRequest.ShortPath request,
		UUID accountId,
		String categoryName
	) {
		Account foundedAccount = accountRepository.findByIdOrThrow(accountId);
		Category foundedCategory = categoryRepository.findByNameOrThrow(categoryName);
		Route tempRoute = Route.createTemporaryRoute(
			foundedAccount,
			foundedCategory
		);
		routeRepository.save(tempRoute);

		routeLinkService.createShortestPaths(
			request.nodeIdSet(),
			request.maxDistance(),
			request.excludeType(),
			tempRoute.getId()
		);
	}
}
