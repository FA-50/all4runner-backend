package com.uos.all4runner.domain.dto.request;

import org.hibernate.validator.constraints.Range;

import com.uos.all4runner.constant.LinkType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface RouteRequest {
	@Schema(name = "RouteRequest.ShortRoute")
	record ShortRoute(
		@NotBlank String nodeIdSet,
		@Min(value = 0) int maxDistance,
		@NotNull LinkType excludeType
		){
	}

	@Schema(name = "RouteRequest.OptimalRoute")
	record OptimalRoute(
		@NotBlank String nodeIdSet,
		@Min(value = 0) int maxDistance,
		@Range(min = 0, max = 20, message = "경사도는 0~20도 범위 이내로 지정해야합니다.") int slopeConstraints,
		@NotNull LinkType excludeType
	){
	}

	@Schema(name = "RouteRequest.TempToPublic")
	record TempToPublic(
		String description,
		@NotBlank String categoryName
		){
	}

	@Schema(name = "RouteRequest.UpdateDescription")
	record UpdateDescription(
		String description
	){
	}
}
