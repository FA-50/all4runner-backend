package com.uos.all4runner.service.route;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.RouteRequest;

public interface RouteService {

	@PreAuthorize("#accountId == authentication.principal.id")
	void createShortestRoute(
		RouteRequest.ShortPath request,
		UUID accountId,
		String categoryName
	);
}
