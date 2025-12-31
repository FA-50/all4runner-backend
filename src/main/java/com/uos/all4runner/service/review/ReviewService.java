package com.uos.all4runner.service.review;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.ReviewRequest;
import com.uos.all4runner.domain.dto.response.ReviewResponse;

public interface ReviewService {

	@PreAuthorize("#accountId == authentication.principal.id")
	void createReview(UUID accountId, UUID routeId, ReviewRequest.Create request);

	@PreAuthorize("#accountId == authentication.principal.id")
	void updateReview(UUID accountId, UUID reviewId, ReviewRequest.Update request);

	@PreAuthorize("#accountId == authentication.principal.id")
	Page<ReviewResponse.Search> getReviewsByMember(UUID accountId, UUID routeId, Pageable pageable);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	Page<ReviewResponse.Search> getReviewsByAdmin(UUID routeId, Pageable pageable);

	@PreAuthorize("#accountId == authentication.principal.id")
	void deleteReviewByMember(UUID accountId, UUID reviewId);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	void deleteReviewByAdmin(UUID reviewId);
}
