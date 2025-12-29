package com.uos.all4runner.service.route;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.RouteRequest;

public interface RouteService {

	@PreAuthorize("#accountId == authentication.principal.id")
	void createShortestRoute(
		RouteRequest.ShortRoute request,
		UUID accountId
	);

	@PreAuthorize("#accountId == authentication.principal.id")
	void createOptimalRoute(
		RouteRequest.OptimalRoute request,
		UUID accountId
	);

	@PreAuthorize("#accountId == authentication.principal.id")
	void updateTempRoute(
		RouteRequest.TempToPublic request,
		UUID accountId,
		UUID routeId
	);
}
