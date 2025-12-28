package com.uos.all4runner.util;

import com.uos.all4runner.domain.entity.category.Category;

public class CategoryCreation {
	public static Category createCategory(){
		return Category.createParentCategory("최상위카테고리");
	}
}
