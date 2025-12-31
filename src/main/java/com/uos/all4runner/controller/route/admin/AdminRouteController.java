package com.uos.all4runner.controller.route.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.response.RouteResponse;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.service.route.RouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/routes")
public class AdminRouteController implements AdminRouteSwaggerSupporter{

	private final RouteService routeService;

	@Override
	@GetMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<RouteResponse.Details>> getRouteDetails(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@PathVariable UUID routeId
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
	@GetMapping("/search/{accountId}")
	public ResponseEntity<ApiResultResponse<Page<RouteResponse.Search>>> searchPublicRoutes(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@ModelAttribute @Valid Paging paging,
		@PathVariable UUID accountId
	) {
		Page<RouteResponse.Search> result = routeService.searchRoutes(
			defaultCurrentUser.getId(),
			accountId,
			paging.getPageable()
		);

		return ApiResultResponse.page(
			SuccessCode.ROUTE_DATA_RESPONSE_SUCCESS,
			result
		);
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
}
