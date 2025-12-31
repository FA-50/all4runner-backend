package com.uos.all4runner.controller.node;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.NodeRequest;
import com.uos.all4runner.domain.dto.response.NodeResponse;
import com.uos.all4runner.service.node.NodeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nodes")
public class NodeController implements NodeSwaggerSupporter {
	private final NodeService nodeService;

	@GetMapping
	public ResponseEntity<ApiResultResponse<NodeResponse.Node>> getClosestNode(
		@RequestBody @Valid NodeRequest.Node request
	) {
		return ApiResultResponse.data(
			SuccessCode.NODE_DATA_RESPONSE_SUCCESS,
			nodeService.getClosestNode(request)
		);
	}
}
