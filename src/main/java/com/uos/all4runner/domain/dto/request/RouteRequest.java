package com.uos.all4runner.domain.dto.request;

import com.uos.all4runner.constant.LinkType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public interface RouteRequest {
	@Schema(name = "RouteRequest.ShortPath")
	record ShortPath(
		@NotBlank String nodeIdSet,
		@NotBlank int distance,
		@NotBlank LinkType excludeType
		){
	}
	@Schema(name = "RouteRequest.OptimalPath")
	record OptimalPath(
		@NotBlank String nodeIdSet,
		@NotBlank int distance,
		@NotBlank int slopeConstraints,
		@NotBlank LinkType excludeType
	){
	}
}
