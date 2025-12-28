package com.uos.all4runner.service.routelink;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.LinkType;

import com.uos.all4runner.repository.routelink.RouteLinkRepository;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteLinkServiceImpl implements RouteLinkService {

	private static String DIJKSTRA_SQL = """
			select
				cast(id as bigint) as id,
				cast(fnode as bigint) as source,
				cast(tnode as bigint) as target,
				%s
				from linknetwork
			""";

	private final RouteLinkRepository routeLinkRepository;

	@Override
	public void createShortestPaths(
		String nodeIdSet,
		int distance,
		LinkType excludeType,
		UUID routeId
	) {
		Long[] nodeIds = Arrays.stream(nodeIdSet.split(","))
			.map(Long::parseLong)
			.toArray(Long[]::new);

		PreConditions.validate(
			nodeIds.length > 1,
			ErrorCode.NODE_NOT_INCLUDE
		);

		String cost = getShortestCost(excludeType);

		routeLinkRepository.createShortestPaths(
			nodeIds,
			distance,
			DIJKSTRA_SQL.formatted(cost),
			routeId
		);
	}

	@Override
	public void createOptimalPaths(
		String nodeIdSet,
		int distance,
		int slopeConstraints,
		LinkType excludeType,
		UUID routeId
	) {
	}

	public String getShortestCost(LinkType excludeType){
		String cost = (excludeType.equals(LinkType.FOOTPATH))?
			"link_cost as cost" :
			"(CASE when link_type = '%s' then 999999 else link_cost END) as cost".formatted(excludeType.name());

		return cost;
	};
}
