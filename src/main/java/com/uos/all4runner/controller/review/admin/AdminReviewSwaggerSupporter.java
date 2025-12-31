package com.uos.all4runner.controller.review.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminReview", description = "관리자용 리뷰 관련 Api")
public interface AdminReviewSwaggerSupporter {

	@Operation(
		summary = "리뷰를 조회",
		description = "리뷰를 조회하는 API",
		parameters = {
			@Parameter(name = "routeId" , description = "경로ID"),
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수")
		}
	)
	ResponseEntity<ApiResultResponse<Page<ReviewResponse.Search>>> getReviews(
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
	ResponseEntity<ApiResultResponse<Void>> deleteReview(UUID reviewId);
}
