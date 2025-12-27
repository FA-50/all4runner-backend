package com.uos.all4runner.service.node;

import com.uos.all4runner.domain.dto.request.NodeRequest;
import com.uos.all4runner.domain.dto.response.NodeResponse;

public interface NodeService {
	NodeResponse.Node  getClosestNode(NodeRequest.Node request);
}
