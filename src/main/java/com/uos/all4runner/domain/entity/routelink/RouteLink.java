package com.uos.all4runner.domain.entity.routelink;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uos.all4runner.constant.LinkType;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "routelink")
public class RouteLink {

	@Id
	@UuidGenerator
	@Column(name = "id", nullable = false, updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected UUID id;

	@Column(nullable = false, columnDefinition = "text")
	private String geojson;

	@Column(nullable = false)
	private double linkLength;

	@Column(nullable = false)
	private double linkSlope;

	@Column(nullable = false)
	private double linkCost;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LinkType linkType;

	@Column(nullable = false)
	private String drinkToilet;

	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;
}
