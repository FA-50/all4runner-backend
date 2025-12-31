package com.uos.all4runner.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public interface ReviewRequest {
	@Schema(name = "ReviewRequest.Create")
	record Create(
		@NotBlank String content
	){
	}

	@Schema(name = "ReviewRequest.Update")
	record Update(
		@NotBlank String content
	){
	}
}
