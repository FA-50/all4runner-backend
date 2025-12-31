package com.uos.all4runner.controller.review.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.service.review.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController implements AdminReviewSwaggerSupporter {

	private final ReviewService reviewService;

	@Override
	@GetMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<Page<ReviewResponse.Search>>> getReviews(
		@PathVariable UUID routeId,
		@ModelAttribute @Valid Paging paging
	) {
		Page<ReviewResponse.Search> result = reviewService.getReviewsByAdmin(
			routeId,
			paging.getPageable()
		);

		return ApiResultResponse.page(
			SuccessCode.REVIEW_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<ApiResultResponse<Void>> deleteReview(
		@PathVariable UUID reviewId
	) {
		reviewService.deleteReviewByAdmin(reviewId);

		return ApiResultResponse.empty(SuccessCode.REVIEW_DELETE_SUCCESS);
	}
}
