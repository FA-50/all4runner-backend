package com.uos.all4runner.controller.node;

import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.request.NodeRequest;
import com.uos.all4runner.domain.dto.response.NodeResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Node", description = "노드 관련 Api")
public interface NodeSwaggerSupporter extends SwaggerSupoorter {

	@Operation(
		summary = "위치와 최근접한 노드ID를 반환",
		description = "제시된 위도, 경도에 최근접한 NodeId를 반환하는 API"
	)
	ResponseEntity<ApiResultResponse<NodeResponse.Node>> getClosestNode(
		NodeRequest.Node request
	);
}
