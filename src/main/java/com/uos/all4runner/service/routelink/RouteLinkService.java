package com.uos.all4runner.service.routelink;

import java.util.List;
import java.util.UUID;

import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.domain.entity.routelink.RouteLink;

import jakarta.validation.constraints.NotBlank;

public interface RouteLinkService {
	List<RouteLink> createShortestPaths(
		String nodeIdSet,
		int distance,
		LinkType excludeType,
		UUID routeId
	);

	List<RouteLink> createOptimalPaths(
		String nodeIdSet,
		int distance,
		int slopeConstraints,
		LinkType excludeType,
		UUID routeId
	);
}
