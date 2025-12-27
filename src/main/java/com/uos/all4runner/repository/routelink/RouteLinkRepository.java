package com.uos.all4runner.repository.routelink;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.routelink.RouteLink;

@Repository
public interface RouteLinkRepository extends JpaRepository<RouteLink, UUID> {
	@Query(value = """
		select
			uuid_generate_v4() as id,
			st_asgeojson(x.geom) as geojson,
			link_length,
			link_slope,
			link_cost,
			link_type,
			drink_toilet,
			:route_id
			from linknetwork as x
		join (
  		select *  
				from pgr_dijkstraVia(
					'select
								cast(id as bigint) as id,
								cast(fnode as bigint) as source,
								cast(tnode as bigint) as target,
								:constrained_cost
								from linknetwork',
					:node_id ::bigint[]
			)
			where agg_cost < :distance
  	) as y
		on x.id = y.edge
		order by y.seq
		""", nativeQuery = true)
	List<RouteLink> createShortestPaths(
		@Param("node_id") Long[] nodeId,
		@Param("distance") int distance,
		@Param("constrained_cost") String constrainedCost,
		@Param("route_id") UUID routeId
	);
}
