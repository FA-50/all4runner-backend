package com.uos.all4runner.repository.review;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.review.Review;
import com.uos.all4runner.exception.CustomException;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {
	default Review findByIdOrThrow(UUID reviewId) {
		return findById(reviewId).orElseThrow(
			()-> new CustomException(ErrorCode.REVIEW_NOT_FOUND)
		);
	}
}
