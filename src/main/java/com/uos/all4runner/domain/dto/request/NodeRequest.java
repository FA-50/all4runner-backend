package com.uos.all4runner.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public interface NodeRequest {
	@Schema(name = "NodeRequest.Node")
	record Node(
		@NotBlank Double longitude,
		@NotBlank Double latitude
	){
	}
}
