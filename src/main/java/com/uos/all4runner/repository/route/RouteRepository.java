package com.uos.all4runner.repository.route;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.route.Route;
import com.uos.all4runner.exception.CustomException;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {
	default Route findByIdOrThrow(UUID accountId) {
		return findById(accountId).orElseThrow(
			()-> new CustomException(ErrorCode.ROUTE_NOT_FOUND)
		);
	}
}
