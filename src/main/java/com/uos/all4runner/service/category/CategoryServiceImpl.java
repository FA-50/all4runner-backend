package com.uos.all4runner.service.category;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.dto.request.CategoryRequest;
import com.uos.all4runner.domain.dto.response.CategoryResponse;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.repository.category.CategoryRepository;
import com.uos.all4runner.util.PreConditions;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryResponse.Search> searchRootCategory() {
		return categoryRepository
			.findRootCateories()
			.stream()
			.map(
				(category) -> new CategoryResponse.Search(
					category.getId(),
					category.getName()
				)
			)
			.toList();
	}

	@Override
	public List<CategoryResponse.Search> searchChildCategory(UUID categoryId) {
		Category foundedCategory = categoryRepository.findByIdOrThrow(categoryId);

		PreConditions.validate(
			!foundedCategory.getCategories().isEmpty(),
			ErrorCode.CATEGORY_NOT_FOUND
		);

		return foundedCategory
			.getCategories()
			.stream()
			.map(
				(category) -> new CategoryResponse.Search(
					category.getId(),
					category.getName()
				)
			)
			.toList();
	}

	@Override
	public CategoryResponse.Search searchParentCategory(UUID categoryId) {
		Category foundedCategory = categoryRepository.findByIdOrThrow(categoryId);

		PreConditions.validate(
			foundedCategory.getParentCategory() != null,
			ErrorCode.CATEGORY_NOT_FOUND
		);

		return new CategoryResponse.Search(
			foundedCategory.getParentCategory().getId(),
			foundedCategory.getParentCategory().getName()
		);
	}

	@Override
	public void createRootCategory(CategoryRequest.Create request) {
		categoryRepository.save(
			Category.createCategory(request.categoryName())
		);
	}

	@Override
	public void createChildCategory(CategoryRequest.Create request, UUID categoryId) {
		Category foundedCategory = categoryRepository.findByIdOrThrow(categoryId);
		categoryRepository.save(
			Category.createCategory(
				request.categoryName(),
				foundedCategory
			)
		);
	}

	@Override
	public void updateCategory(CategoryRequest.Update request, UUID categoryId) {
		Category foundedCategory = categoryRepository.findByIdOrThrow(categoryId);
		foundedCategory.update(request.categoryName());
	}

	@Override
	public void deleteCategory(UUID categoryId) {
		categoryRepository.deleteById(categoryId);
	}
}
