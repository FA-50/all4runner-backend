package com.uos.all4runner.repository.node;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.entity.node.Node;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NodeRepositoryTest {
	@Autowired
	NodeRepository nodeRepository;

	@Test
	void 좌표에서_근접한_노드검색__성공(){
		Node foundedNode = nodeRepository.findByCoordOrThrow(127.0489469, 37.5799645);

		Assertions.assertNotNull(foundedNode);
		Assertions.assertEquals(343628l, foundedNode.getId());
	}

	@Test
	void 좌표에서_버퍼에_해당하는_노드검색__성공(){
		List<Node> foundedNodes = nodeRepository.findByDistance(
			127.0489469,
			37.5799645,
			520l,
			510l
		);

		Assertions.assertTrue(!foundedNodes.isEmpty());
	}
}