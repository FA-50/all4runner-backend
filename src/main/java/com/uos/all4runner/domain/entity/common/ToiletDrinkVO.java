package com.uos.all4runner.domain.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ToiletDrinkVO {
	@Column(nullable = false)
	private double toiletLat;

	@Column(nullable = false)
	private double toiletLong;

	@Column(nullable = false)
	private double drinkLat;

	@Column(nullable = false)
	private double drinkLong;
}
