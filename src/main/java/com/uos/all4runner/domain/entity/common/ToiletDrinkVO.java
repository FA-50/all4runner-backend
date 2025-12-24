package com.uos.all4runner.domain.entity.common;

import jakarta.persistence.Embeddable;

@Embeddable
public class ToiletDrinkVO {
	private double toiletLat;

	private double toiletLong;

	private double drinkLat;

	private double drinkLong;
}
