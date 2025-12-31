package com.uos.all4runner.controller.review;

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
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.request.Paging;
import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.ReviewRequest;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.service.review.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewSwaggerSupporter {

	private final ReviewService reviewService;

	@PostMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<Void>> createReview(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@Valid @RequestBody ReviewRequest.Create request,
		@PathVariable UUID routeId
	){
		reviewService.createReview(
			defaultCurrentUser.getId(),
			routeId,
			request
		);

		return ApiResultResponse.empty(SuccessCode.REVIEW_CREATE_SUCCESS);
	}

	@Override
	@PatchMapping("/{reviewId}")
	public ResponseEntity<ApiResultResponse<Void>> updateReview(
		@AuthenticationPrincipal	DefaultCurrentUser defaultCurrentUser,
		@RequestBody @Valid ReviewRequest.Update request,
		@PathVariable UUID reviewId
	) {
		reviewService.updateReview(
			defaultCurrentUser.getId(),
			reviewId,
			request
		);

		return ApiResultResponse.empty(SuccessCode.REVIEW_UPDATE_SUCCESS);
	}

	@Override
	@GetMapping("/{routeId}")
	public ResponseEntity<ApiResultResponse<Page<ReviewResponse.Search>>> getReviews(
		@AuthenticationPrincipal DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID routeId,
		@ModelAttribute @Valid Paging paging
	) {
		Page<ReviewResponse.Search> result = reviewService.getReviewsByMember(
			defaultCurrentUser.getId(),
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
		@AuthenticationPrincipal	DefaultCurrentUser defaultCurrentUser,
		@PathVariable	UUID reviewId
	) {
		reviewService.deleteReviewByMember(
			defaultCurrentUser.getId(),
			reviewId
		);

		return ApiResultResponse.empty(SuccessCode.REVIEW_DELETE_SUCCESS);
	}

}
