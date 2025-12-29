package com.uos.all4runner.domain.entity.category;

import com.uos.all4runner.domain.entity.common.BaseEntity;
import com.uos.all4runner.domain.entity.route.Route;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category extends BaseEntity {
	
	public static final String UNASSIGNED = "카테고리없음";
	
	@Column(nullable = false)
	private String name;

	@OneToMany(
		mappedBy = "parentCategory",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Category> categorys = new ArrayList<Category>();

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parentCategory;

	@OneToMany(mappedBy = "category")
	private List<Route> routes = new ArrayList<Route>();

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Category parentCategory) {
		this.name = name;
		this.parentCategory = parentCategory;
	}

	public static Category createCategory(String name){
		return new Category(name);
	}

	public static Category createCategory(
		String name,
		Category parentCategory
	){
		Category category = new Category(name, parentCategory);
		category.parentCategory.mapChildCategory(category);
		return category;
	}

	public void mapToCategory(Route route){
		routes.add(route);
	}

	public void mapChildCategory(Category childCategory) {
		this.categorys.add(childCategory);
	}
}
