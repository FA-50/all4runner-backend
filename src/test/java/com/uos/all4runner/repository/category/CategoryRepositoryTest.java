package com.uos.all4runner.repository.category;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.domain.entity.category.Category;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	Category category;
	Category childCategory;

	@BeforeEach
	void setUp() throws Exception {
		categoryRepository.deleteAll();
		category = Category.createCategory("최상위카테고리");
		categoryRepository.save(category);
		childCategory = Category.createCategory("자식카테고리",category);
		categoryRepository.save(childCategory);
	}

	@Test
	void 최상위카테고리_조회_성공(){
		// when
		List<Category> foundedCategories = categoryRepository.findRootCateories();

		// then
		Assertions.assertTrue(foundedCategories.size() == 1);
		Assertions.assertEquals(category.getId(), foundedCategories.get(0).getId());
	}
}