package com.uos.all4runner.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public interface CategoryRequest {
	@Schema(name = "CategoryRequest.Create")
	record Create(
		@NotBlank String categoryName
	){
	}

	@Schema(name = "CategoryRequest.Update")
	record Update(
		@NotBlank String categoryName
	){
	}
}
