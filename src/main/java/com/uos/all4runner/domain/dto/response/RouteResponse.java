package com.uos.all4runner.domain.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.entity.routelink.RouteLink;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public interface RouteResponse {
	@Schema(name = "RouteResponse.CreateTemp")
	record CreateTemp(
		UUID routeId
	){
	}

	@Schema(name = "RouteResponse.Search")
	record Search(
		UUID routeId,
		String description,
		String categoryName
	) {
		@QueryProjection
		public Search{
		}
	}

	@Schema(name = "RouteResponse.Details")
	record Details(
		UUID routeId,
		RouteStatus routeStatus,
		String description,
		String categoryName,
		String writerName,
		Double estimatedKcal,
		Double estimatedRunningTime,
		List<RouteLink> routeLinks,
		Instant createdAt
	){
	}
}
