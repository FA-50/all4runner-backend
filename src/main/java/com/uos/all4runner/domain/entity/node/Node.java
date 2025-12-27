package com.uos.all4runner.domain.entity.node;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "nodenetwork")
public class Node {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;

	@Column(nullable = false, columnDefinition = "text")
	private String geom;
}
