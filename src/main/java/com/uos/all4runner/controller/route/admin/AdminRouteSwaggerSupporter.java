package com.uos.all4runner.controller.route.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.controller.route.RouteSwaggerSupporter;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminRoute", description = "관리자용 경로 관련 Api")
public interface AdminRouteSwaggerSupporter extends SwaggerSupoorter {
	@Operation(
		summary = "경로를 상세조회",
		description = "경로ID에 해당하는 경로를 상세조회하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<RouteResponse.Details>> getRouteDetails(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	);

	@Operation(
		summary = "경로를 검색",
		description = "특정 계정이 가진 경로를 검색하는 API",
		parameters = {
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수"),
			@Parameter(name = "accountId" , description = "계정ID")
		}
	)
	ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchPublicRoutes(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		Paging paging,
		UUID accountId
	);

	@Operation(
		summary = "경로를 삭제",
		description = "경로ID의 경로를 삭제하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> deleteRoute(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	);
}
