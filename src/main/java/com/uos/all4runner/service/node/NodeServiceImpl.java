package com.uos.all4runner.service.node;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.dto.request.NodeRequest;
import com.uos.all4runner.domain.dto.response.NodeResponse;
import com.uos.all4runner.domain.entity.node.Node;
import com.uos.all4runner.repository.node.NodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NodeServiceImpl implements NodeService {

	private final NodeRepository nodeRepository;

	@Override
	@PreAuthorize("hasRole({'MEMBER', 'ADMIN', 'SUPERADMIN'})")
	public NodeResponse.Node getClosestNode(NodeRequest.Node request) {
		Node foundedNode = nodeRepository.findByCoordOrThrow(
			request.longitude(),
			request.latitude()
		);
		return new NodeResponse.Node(foundedNode.getId());
	}
}
