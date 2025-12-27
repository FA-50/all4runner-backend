package com.uos.all4runner.service.routelink;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import com.uos.all4runner.repository.routelink.RouteLinkRepository;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteLinkServiceImpl implements RouteLinkService {

	private final RouteLinkRepository routeLinkRepository;

	@Override
	public List<RouteLink> createShortestPaths(
		String nodeIdSet,
		int distance,
		LinkType excludeType,
		UUID routeId
	) {

		Long[] nodeId = Arrays.stream(nodeIdSet.split(","))
			.map(Long::parseLong)
			.toArray(Long[]::new);

		PreConditions.validate(
			nodeId.length > 1,
			ErrorCode.NODE_NOT_INCLUDE
		);

		String constrainedCost = setConstraints(excludeType);

		return routeLinkRepository.createShortestPaths(nodeId, distance, constrainedCost, routeId);
	}

	@Override
	public List<RouteLink> createOptimalPaths(
		String nodeIdSet,
		int distance,
		int slopeConstraints,
		LinkType excludeType,
		UUID routeId
	) {
		return List.of();
	}

	public String setConstraints(LinkType excludeType){
		return (excludeType.equals(LinkType.FOOTPATH))?
			"link_cost as cost":
		"(CASE when link_type = ''%s'' then 999999 else link_cost END) as cost".formatted(excludeType.name());
	};
}
