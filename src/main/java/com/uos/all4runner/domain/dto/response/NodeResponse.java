package com.uos.all4runner.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface NodeResponse {
	@Schema(name = "NodeResponse.Node")
	record Node(
		Long nodeId
	){
	}
}
