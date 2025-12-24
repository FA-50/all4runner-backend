package com.uos.all4runner.domain.entity.routelink;

import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.common.ToiletDrinkVO;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "routelink")
public class RouteLink extends BaseEntity {

	@Column(nullable = false)
	private String geojson;

	@Column(nullable = false)
	private double linkLength;

	@Column(nullable = false)
	private double linkSlope;

	@Column(nullable = false)
	private double linkCost;

	@Column(nullable = false)
	private double linkKcal;

	@Column(nullable = false)
	private LinkType linkType;

	@Embedded
	private ToiletDrinkVO toiletDrinkVO;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;
}
