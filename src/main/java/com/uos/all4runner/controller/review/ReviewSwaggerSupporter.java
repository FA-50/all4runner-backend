package com.uos.all4runner.controller.review;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.request.ReviewRequest;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Review", description = "리뷰 관련 Api")
public interface ReviewSwaggerSupporter extends SwaggerSupoorter {

	@Operation(
		summary = "해당 경로에 리뷰를 생성",
		description = "경로에 리뷰를 등록하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> createReview(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		ReviewRequest.Create request,
		UUID routeId
	);

	@Operation(
		summary = "리뷰를 수정",
		description = "리뷰를 수정하는 API",
		parameters = {
			@Parameter(name = "reviewId" , description = "리뷰ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> updateReview(
			@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
			ReviewRequest.Update request,
			UUID reviewId
		);

	@Operation(
		summary = "리뷰를 조회",
		description = "리뷰를 조회하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID"),
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수")
		}
	)
	ResponseEntity<ApiResultResponse<Page<ReviewResponse.Search>>> getReviews(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID routeId,
		Paging paging
	);

	@Operation(
		summary = "리뷰를 삭제",
		description = "리뷰를 삭제하는 API",
		parameters = {
			@Parameter(name = "reviewId" , description = "리뷰ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> deleteReview(
		@Parameter(hidden = true) DefaultCurrentUser defaultCurrentUser,
		UUID reviewId
	);
}
