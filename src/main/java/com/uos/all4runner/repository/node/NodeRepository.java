package com.uos.all4runner.repository.node;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.node.Node;
import com.uos.all4runner.exception.CustomException;

public interface NodeRepository extends JpaRepository<Node,Long> {

	@Query(value = """
		SELECT n
					FROM Node n
					ORDER BY ST_Distance(
							n.geom,
							ST_SetSRID(
									ST_MakePoint(?1,?2),
											 4326
									)
							)
					LIMIT 1
		""")
	Optional<Node> findByCoord(Double longitude, Double latitude);

	default Node findByCoordOrThrow(Double longitude, Double latitude) {
		return findByCoord(longitude, latitude).orElseThrow(
			()-> new CustomException(ErrorCode.NODE_NOT_FOUND)
		);
	}

	@Query(value = """
			 	WITH cte_buffer as (
                SELECT ST_transform(
		                ST_Difference(
											ST_Buffer(ST_transform(ST_SetSRID(ST_MakePoint(:longitude,:latitude), 4326),5174), :inner_distance),  
											ST_Buffer(ST_transform(ST_SetSRID(ST_MakePoint(:longitude,:latitude), 4326),5174), :outer_distance)   
                ),4326) AS geom)
				SELECT n.*
						FROM nodenetwork n
						JOIN cte_buffer cte
						on ST_Within(n.geom,cte.geom)
		""", nativeQuery = true)
	List<Node> findByDistance(
		@Param("longitude") Double longitude,
		@Param("latitude") Double latitude,
		@Param("inner_distance") Double innerDistance,
		@Param("outer_distance") Double outerDistance
	);
}
