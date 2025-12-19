package com.uos.all4runner.repository.route;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.route.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {
}
