package com.uos.all4runner.domain.entity.network;


import com.uos.all4runner.constant.LinkType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "linknetwork")
public class Network {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;

	@Column(nullable = false)
	private long fnode;

	@Column(nullable = false)
	private long tnode;

	@Column(nullable = false, columnDefinition = "text")
	private String geom;

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
}
