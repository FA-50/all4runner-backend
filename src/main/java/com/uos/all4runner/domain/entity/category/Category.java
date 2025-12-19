package com.uos.all4runner.domain.entity.category;

import com.uos.all4runner.common.BaseEntity;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@OneToMany(
		mappedBy = "parentCategory",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Category> category = new ArrayList<Category>();

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parentCategory;

	@OneToMany(mappedBy = "parentCategory")
	private List<Route> routes = new ArrayList<Route>();
}
