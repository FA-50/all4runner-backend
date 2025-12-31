package com.uos.all4runner.domain.dto.response;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ReviewResponse {
	@Schema(name = "ReviewResponse.Search")
	record Search(
		String content,
		UUID reviewId,
		String writerName
	){
		@QueryProjection
		public Search{
		}
	}
}
