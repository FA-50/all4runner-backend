package com.uos.all4runner.service.node;

import org.springframework.security.access.prepost.PreAuthorize;

import com.uos.all4runner.domain.dto.request.NodeRequest;
import com.uos.all4runner.domain.dto.response.NodeResponse;

public interface NodeService {
	@PreAuthorize("hasAnyRole('MEMBER', 'ADMIN', 'SUPERADMIN')")
	NodeResponse.Node  getClosestNode(NodeRequest.Node request);
}
