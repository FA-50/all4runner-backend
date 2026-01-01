package com.uos.all4runner.domain.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CategoryResponse {
	@Schema(name = "CategoryResponse.Search")
	record Search(
		UUID categoryId,
		String categoryName
	){
	}

}
