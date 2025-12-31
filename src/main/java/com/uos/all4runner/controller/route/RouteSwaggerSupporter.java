package com.uos.all4runner.controller.route;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Route", description = "경로 관련 Api")
public interface RouteSwaggerSupporter extends SwaggerSupoorter {

	@Operation(
		summary = "목적노드까지 최단경로를 생성합니다.",
		description = "로그인한 계정의 최단경로를 생성하는 API"
	)
	ResponseEntity<ApiResultResponse<RouteResponse.CreateTemp>> createShortestRoute(
		RouteRequest.ShortRoute request,
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser
	);

	@Operation(
		summary = "목적노드까지 최적경로를 생성합니다.",
		description = "로그인한 계정의 최적경로를 생성하는 API"
	)
	ResponseEntity<ApiResultResponse<RouteResponse.CreateTemp>> createOptimalRoute(
		RouteRequest.OptimalRoute request,
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser
	);

	@Operation(
		summary = "해당 계정의 RouteStatus = TEMP의 경로를 모두 삭제",
		description = "로그인한 계정의 임시경로를 모두 삭제하는 API"
	)
	ResponseEntity<ApiResultResponse<Void>> deleteTempRoute(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser
	);

	@Operation(
		summary = "해당 계정의 RouteStatus = TEMP의 경로를 PUBLIC 경로로 등록",
		description = "로그인한 계정의 임시경로를 정상 경로로 등록하는 API",
		parameters = {
			@Parameter(name = "tempRouteId" , description = "임시경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> updateTempRoute(
		RouteRequest.TempToPublic request,
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID tempRouteId
	);

	@Operation(
		summary = "해당 계정의 모든 경로를 검색",
		description = "로그인한 계정의 모든 경로를 검색하는 API",
		parameters = {
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수")
		}
	)
	ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchMyRoutes(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		Paging paging
	);

	@Operation(
		summary = "해당 계정의 임시경로를 상세조회",
		description = "로그인한 계정의 경로ID에 해당하는 임시경로를 상세조회하는 API",
		parameters = {
			@Parameter(name = "tempRouteId" , description = "임시경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<RouteResponse.TempDetails>> getTempRouteDetails(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID tempRouteId
	);

	@Operation(
		summary = "경로를 상세조회",
		description = """
			경로ID에 해당하는 경로를 상세조회하는 API\n
			(RouteStatus = PUBLIC이 아닐때, 해당 계정의 경로가 아닌 경우 차단)
			""",
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
		description = "RouteStatus = PUBLIC인 경로를 검색하는 API",
		parameters = {
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수"),
			@Parameter(name = "categoryName" , description = "검색할 카테고리명")
		}
	)
	ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchPublicRoutes(
		Paging paging,
		String categoryName
	);

	@Operation(
		summary = "경로의 설명을 수정",
		description = "로그인한 계정의 경로의 설명을 수정하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> updateDescription(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId,
		RouteRequest.UpdateDescription request
	);

	@Operation(
		summary = "경로를 삭제",
		description = "로그인한 계정의 경로를 삭제하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> deleteRoute(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	);

	@Operation(
		summary = "경로상태를 PUBLIC으로 전환",
		description = "로그인한 계정의 경로를 PUBLIC으로 전환",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> setRoutePublic(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	);

	@Operation(
		summary = "경로상태를 PRIVATE으로 전환",
		description = "로그인한 계정의 경로를 PRIVATE으로 전환",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> setRoutePrivate(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	);
}
