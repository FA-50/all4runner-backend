package com.uos.all4runner.service.route;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.dto.response.RouteResponse;

public interface RouteService {

	@PreAuthorize("#accountId == authentication.principal.id")
	RouteResponse.CreateTemp  createShortestRoute(
		RouteRequest.ShortRoute request,
		UUID accountId
	);

	@PreAuthorize("#accountId == authentication.principal.id")
	RouteResponse.CreateTemp  createOptimalRoute(
		RouteRequest.OptimalRoute request,
		UUID accountId
	);

	@PreAuthorize("#accountId == authentication.principal.id")
	void updateTempRoute(
		RouteRequest.TempToPublic request,
		UUID accountId,
		UUID routeId
	);

	@PreAuthorize("#accountId == authentication.principal.id")
	void deleteTempByAccountId(UUID accountId);


	@PreAuthorize("#accountId == authentication.principal.id")
	void updateRouteDescription(UUID accountId, UUID routeId, RouteRequest.UpdateDescription request);

	@PreAuthorize("#accountId == authentication.principal.id")
	void deleteRoute(UUID accountId, UUID routeId);

	@PreAuthorize("#accountId == authentication.principal.id")
	void switchRouteToPublic(UUID accountId, UUID routeId);

	@PreAuthorize("#accountId == authentication.principal.id")
	void switchRouteToPrivate(UUID accountId, UUID routeId);

	@PreAuthorize("#accountId == authentication.principal.id")
	RouteResponse.Details getRouteDetails(UUID accountId, UUID routeId);

	@PreAuthorize("#accountId == authentication.principal.id")
	Page<RouteResponse.Search> searchRoutes(UUID accountId, UUID subjectId, Pageable pageable);

	@PreAuthorize("hasAnyRole('MEMBER', 'ADMIN', 'SUPERADMIN')")
	Page<RouteResponse.Search> searchPublicRoutes(String categoryName, Pageable pageable);
}