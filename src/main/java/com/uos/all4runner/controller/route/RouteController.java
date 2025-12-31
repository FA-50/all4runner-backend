package com.uos.all4runner.controller.route;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.RouteRequest;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.service.route.RouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RouteController implements RouteSwaggerSupporter {

	private final RouteService routeService;

	@Override
	@PostMapping("/short")
	public ResponseEntity<ApiResultResponse<RouteResponse.CreateTemp>> createShortestRoute(
		@RequestBody @Valid RouteRequest.ShortRoute request,
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser
	) {
		RouteResponse.CreateTemp result = routeService.createShortestRoute(
			request,
			defaultCurrentUser.getId()
		);

		return ApiResultResponse.data(
			SuccessCode.TEMP_ROUTE_CREATE_SUCCESS,
			result
		);
	}

	@Override
	@PostMapping("/optimal")
	public ResponseEntity<ApiResultResponse<RouteResponse.CreateTemp>> createOptimalRoute(
		@RequestBody @Valid RouteRequest.OptimalRoute request,
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser
	) {
		RouteResponse.CreateTemp result = routeService.createOptimalRoute(
			request,
			defaultCurrentUser.getId()
		);

		return ApiResultResponse.data(
			SuccessCode.TEMP_ROUTE_CREATE_SUCCESS,
			result
		);
	}

	@Override
	@DeleteMapping("/temp")
	public ResponseEntity<ApiResultResponse<Void>> deleteTempRoute(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser
	) {
		routeService.deleteTempByAccountId(defaultCurrentUser.getId());

		return ApiResultResponse.empty(SuccessCode.TEMP_ROUTE_DELETE_SUCCESS);
	}

	@Override
	@PatchMapping("/temp/{tempRouteId}")
	public ResponseEntity<ApiResultResponse<Void>> updateTempRoute(
		@RequestBody @Valid RouteRequest.TempToPublic request,
		@AuthenticationPrincipal	DefaultCurrentUser defaultCurrentUser,
		@PathVariable UUID tempRouteId
	) {
		routeService.updateTempRoute(
			request,
			defaultCurrentUser.getId(),
			tempRouteId
		);

		return ApiResultResponse.empty(SuccessCode.TEMP_ROUTE_UPDATE_SUCCESS);
	}

	@Override
	@GetMapping
	public ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchMyRoutes(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@ModelAttribute @Valid Paging paging
	) {
		Page<RouteResponse.Search> result =	routeService.searchRoutes(
			defaultCurrentUser.getId(),
			defaultCurrentUser.getId(),
			paging.getPageable()
		);

		return ApiResultResponse.page(
			SuccessCode.ROUTE_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@GetMapping("/temp/{tempRouteId}")
	public ResponseEntity<ApiResultResponse<RouteResponse.TempDetails>> getTempRouteDetails(
		@AuthenticationPrincipal	DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID tempRouteId
	) {
		RouteResponse.TempDetails result = routeService.getTempRouteDetails(
			defaultCurrentUser.getId(),
			tempRouteId
		);

		return ApiResultResponse.data(
			SuccessCode.ROUTE_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@GetMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<RouteResponse.Details>> getRouteDetails(
	 	@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID routeId
	) {
		RouteResponse.Details result = routeService.getRouteDetails(
			defaultCurrentUser.getId(),
			routeId
		);

		return ApiResultResponse.data(
			SuccessCode.ROUTE_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@GetMapping("/search")
	public ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchPublicRoutes(
		@ModelAttribute @Valid Paging paging,
	 	@RequestParam(required = false) String categoryName
	) {
		Page<RouteResponse.Search> result = routeService
			.searchPublicRoutes(
				categoryName,
				paging.getPageable()
			);

		return ApiResultResponse.page(
			SuccessCode.ROUTE_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@PatchMapping("/{routeId}/description")
	public ResponseEntity<ApiResultResponse<Void>> updateDescription(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID routeId,
	 	@RequestBody	@Valid	RouteRequest.UpdateDescription request
	) {
		routeService.updateRouteDescription(
			defaultCurrentUser.getId(),
			routeId,
			request
		);

		return ApiResultResponse.empty(SuccessCode.ROUTE_UPDATE_SUCCESS);
	}

	@Override
	@DeleteMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<Void>> deleteRoute(
		@AuthenticationPrincipal	DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID routeId
	) {
		routeService.deleteRoute(
			defaultCurrentUser.getId(),
			routeId
		);

		return ApiResultResponse.empty(SuccessCode.ROUTE_DELETE_SUCCESS);
	}

	@Override
	@PatchMapping("/{routeId}/public")
	public ResponseEntity<ApiResultResponse<Void>> setRoutePublic(
		DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	) {
		routeService.switchRouteToPublic(
			defaultCurrentUser.getId(),
			routeId
		);
		return ApiResultResponse.empty(SuccessCode.ROUTE_UPDATE_SUCCESS);
	}

	@Override
	@PatchMapping("/{routeId}/private")
	public ResponseEntity<ApiResultResponse<Void>> setRoutePrivate(
		DefaultCurrentUser defaultCurrentUser,
		UUID routeId
	) {
		routeService.switchRouteToPrivate(
			defaultCurrentUser.getId(),
			routeId
		);
		return ApiResultResponse.empty(SuccessCode.ROUTE_UPDATE_SUCCESS);
	}
}
