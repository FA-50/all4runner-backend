package com.uos.all4runner.service.review;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.constant.RouteStatus;
import com.uos.all4runner.domain.dto.request.ReviewRequest;
import com.uos.all4runner.domain.dto.response.ReviewResponse;
import com.uos.all4runner.domain.entity.review.Review;
import com.uos.all4runner.repository.account.AccountRepository;
import com.uos.all4runner.repository.review.ReviewRepository;
import com.uos.all4runner.repository.route.RouteRepository;

import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final AccountRepository accountRepository;
	private final RouteRepository routeRepository;

	@Override
	public void createReview(UUID accountId, UUID routeId, ReviewRequest.Create request) {
		Route foundedRoute = routeRepository.findByIdOrThrow(routeId);

		PreConditions.validate(
			!foundedRoute.getRouteStatus().equals(RouteStatus.PRIVATE),
			ErrorCode.ROUTE_STATUS_PRIVATE
		);

		reviewRepository.save(
			Review.createReview(
				request.content(),
				accountRepository.findByIdOrThrow(accountId),
				foundedRoute
			)
		);
	}

	@Override
	public void updateReview(UUID accountId, UUID reviewId, ReviewRequest.Update request) {
		Review review = reviewRepository.findByIdOrThrow(reviewId);

		PreConditions.validate(
			review.getWritedBy().getId().equals(accountId),
			ErrorCode.ACCESS_NOT_ALLOWED
		);

		review.updateReview(request.content());
	}

	@Override
	public Page<ReviewResponse.Search> getReviewsByMember(UUID accountId, UUID routeId, Pageable pageable) {
		Route foundedRoute = routeRepository.findByIdOrThrow(routeId);

		if (foundedRoute.getRouteStatus().equals(RouteStatus.PRIVATE))
			PreConditions.validate(
				accountId.equals(foundedRoute.getAccount().getId()),
				ErrorCode.ACCESS_NOT_ALLOWED
			);

		return reviewRepository.searchByRoute(foundedRoute.getId(), pageable);
	}

	@Override
	public Page<ReviewResponse.Search> getReviewsByAdmin(UUID routeId, Pageable pageable) {
		return reviewRepository.searchByRoute(routeId, pageable);
	}

	@Override
	public void deleteReviewByMember(UUID accountId, UUID reviewId) {
		Review foundedReview = reviewRepository.findByIdOrThrow(reviewId);

		PreConditions.validate(
			foundedReview.getWritedBy().getId().equals(accountId),
			ErrorCode.ACCESS_NOT_ALLOWED
		);

		reviewRepository.delete(foundedReview);
	}

	@Override
	public void deleteReviewByAdmin(UUID reviewId) {
		reviewRepository.deleteById(reviewId);
	}
}
