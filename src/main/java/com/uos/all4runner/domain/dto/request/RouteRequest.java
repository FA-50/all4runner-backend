package com.uos.all4runner.domain.dto.request;

import org.hibernate.validator.constraints.Range;

import com.uos.all4runner.constant.LinkType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public interface RouteRequest {
	@Schema(name = "RouteRequest.ShortPath")
	record ShortPath(
		@NotBlank String nodeIdSet,
		@Min(value = 0) int maxDistance,
		@NotBlank LinkType excludeType
		){
	}
	@Schema(name = "RouteRequest.OptimalPath")
	record OptimalPath(
		@NotBlank String nodeIdSet,
		@Min(value = 0) int maxDistance,
		@Range(min = 0, max = 20, message = "경사도는 0~20도 범위 이내로 지정해야합니다.") int slopeConstraints,
		@NotBlank LinkType excludeType
	){
	}
}
