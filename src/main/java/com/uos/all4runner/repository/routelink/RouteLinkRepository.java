package com.uos.all4runner.repository.routelink;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.entity.routelink.RouteLink;

@Repository
public interface RouteLinkRepository extends JpaRepository<RouteLink, UUID> {

	@Modifying
	@Transactional
	@Query(value = """
		insert into routelink(
  	id, geojson, link_length,
  	link_slope, link_cost, link_type,
  	drink_toilet, route_id
		)
		select
			uuid_generate_v4() as id,
			st_asgeojson(x.geom) as geojson,
			link_length,
			link_slope,
			link_cost,
			link_type,
			drink_toilet,
			:route_id as route_id
			from linknetwork as x
		join (
  		select *  
				from pgr_dijkstraVia(
					:dijkstra_sql,
					:node_ids ::bigint[]
			)
			where agg_cost < :max_distance
  	) as y
		on x.id = y.edge
		order by y.seq
		""", nativeQuery = true)
	void createPaths(
		@Param("node_ids") Long[] nodeIds,
		@Param("max_distance") int maxDistance,
		@Param("dijkstra_sql") String dijkstra_sql,
		@Param("route_id") UUID routeId
	);
}
