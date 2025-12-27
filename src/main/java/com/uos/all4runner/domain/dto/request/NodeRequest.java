package com.uos.all4runner.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public interface NodeRequest {
	@Schema(name = "NodeRequest.Node")
	record Node(
		Double longitude,
		Double latitude
	){
	}
}
