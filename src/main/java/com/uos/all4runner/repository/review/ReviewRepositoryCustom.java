package com.uos.all4runner.repository.review;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uos.all4runner.domain.dto.response.ReviewResponse;

public interface ReviewRepositoryCustom {
	Page<ReviewResponse.Search> searchByRoute(UUID routeId, Pageable pageable);
}
