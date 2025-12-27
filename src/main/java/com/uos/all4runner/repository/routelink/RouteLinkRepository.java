package com.uos.all4runner.repository.routelink;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.routelink.RouteLink;

@Repository
public interface RouteLinkRepository extends JpaRepository<RouteLink, UUID> {
	// @Query(value = """
	//
	// 	""", nativeQuery = true)
	// List<RouteLink> findShortestPaths(String fnode, String tnode);
}
