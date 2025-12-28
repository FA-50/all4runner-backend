package com.uos.all4runner.service.routelink;

import java.util.UUID;

import com.uos.all4runner.constant.LinkType;

public interface RouteLinkService {
	void createShortestPaths(
		String nodeIdSet,
		int distance,
		LinkType excludeType,
		UUID routeId
	);

	void createOptimalPaths(
		String nodeIdSet,
		int distance,
		int slopeConstraints,
		LinkType excludeType,
		UUID routeId
	);
}
