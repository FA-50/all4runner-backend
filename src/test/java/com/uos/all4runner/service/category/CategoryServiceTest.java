package com.uos.all4runner.service.category;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.domain.dto.request.CategoryRequest;
import com.uos.all4runner.domain.dto.response.CategoryResponse;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.util.AuthenticationCreation;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryRepository categoryRepository;

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
		List<CategoryResponse.Search> foundedCategories = categoryService.searchRootCategory();

		// then
		Assertions.assertFalse(foundedCategories.isEmpty());
		Assertions.assertEquals(category.getId(), foundedCategories.get(0).categoryId());
	}

	@Test
	void 자식카테고리_조회_성공(){
		// when
		List<CategoryResponse.Search> foundedChildCategories = categoryService
			.searchChildCategory(category.getId());

		// then
		Assertions.assertTrue(foundedChildCategories.size() == 1);
		Assertions.assertEquals(childCategory.getId(), foundedChildCategories.get(0).categoryId());
	}

	@Test
	void 부모카테고리_조회_성공(){
		// when
		CategoryResponse.Search foundedParentCategories = categoryService
			.searchParentCategory(childCategory.getId());

		// then
		Assertions.assertNotNull(foundedParentCategories);
		Assertions.assertEquals(category.getId(), foundedParentCategories.categoryId());
	}

	@Test
	void 최상위카테고리_생성_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			UUID.randomUUID(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		categoryService.createRootCategory(
			new CategoryRequest.Create("새로운카테고리")
		);

		// then
		Category foundedCategory = categoryRepository.findByNameOrThrow("새로운카테고리");
		Assertions.assertNull(foundedCategory.getParentCategory());
	}

	@Test
	void 최상위카테고리_생성_실패__권한없음(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			UUID.randomUUID(),
			AccountRole.MEMBER
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		assertThatThrownBy(
			()-> categoryService.createRootCategory(
				new CategoryRequest.Create("새로운카테고리")
			)
		)
		// then
			.isInstanceOf(AuthorizationDeniedException.class);
	}

	@Test
	void 자식카테고리_생성_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			UUID.randomUUID(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		categoryService.createChildCategory(
			new CategoryRequest.Create("새로운카테고리"),
			category.getId()
		);

		// then
		Category foundedCategory = categoryRepository.findByNameOrThrow("새로운카테고리");
		Assertions.assertEquals(category.getId(), foundedCategory.getParentCategory().getId());
	}

	@Test
	void 카테고리_수정_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			UUID.randomUUID(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		categoryService.updateCategory(
			new CategoryRequest.Update("수정된카테고리"),
			category.getId()
		);

		// then
		Category foundedCategory = categoryRepository.findByNameOrThrow("수정된카테고리");
		Assertions.assertEquals(category.getId(), foundedCategory.getId());
	}

	@Test
	void 카테고리_삭제_성공(){
		// begin
		TestingAuthenticationToken testAuth = AuthenticationCreation.createTestAuthentication(
			UUID.randomUUID(),
			AccountRole.ADMIN
		);
		SecurityContextHolder.getContext().setAuthentication(testAuth);

		// when
		categoryService.deleteCategory(category.getId());

		// then
		Assertions.assertNull(categoryRepository.findById(category.getId()).orElse(null));
		Assertions.assertNull(categoryRepository.findById(childCategory.getId()).orElse(null));
	}
}