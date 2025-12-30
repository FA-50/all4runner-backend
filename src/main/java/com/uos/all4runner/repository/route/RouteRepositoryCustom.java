package com.uos.all4runner.repository.route;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uos.all4runner.domain.dto.response.RouteResponse;

public interface RouteRepositoryCustom {
	Page<RouteResponse.Search> searchRoutesByAccountId(UUID accountId, Pageable pageable);

	Page<RouteResponse.Search> searchPublicRoutes(String categoryName, Pageable pageable);
}
