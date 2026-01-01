package com.uos.all4runner.controller.category;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.response.CategoryResponse;
import com.uos.all4runner.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController implements CategorySwaggerSupporter {

	private final CategoryService categoryService;

	@Override
	@GetMapping
	public ResponseEntity<ApiResultResponse<List<CategoryResponse.Search>>> getRootCategory() {
		List<CategoryResponse.Search> result = categoryService.searchRootCategory();

		return ApiResultResponse.data(
			SuccessCode.CATEGORY_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@GetMapping("/{categoryId}/child")
	public ResponseEntity<ApiResultResponse<List<CategoryResponse.Search>>> getChildCategory(
		@PathVariable UUID categoryId
	) {
		List<CategoryResponse.Search> result = categoryService.searchChildCategory(categoryId);

		return ApiResultResponse.data(
			SuccessCode.CATEGORY_DATA_RESPONSE_SUCCESS,
			result
		);
	}

	@Override
	@GetMapping("/{categoryId}/parent")
	public ResponseEntity<ApiResultResponse<CategoryResponse.Search>> getParentCategory(
		@PathVariable	UUID categoryId
	) {
		CategoryResponse.Search result = categoryService.searchParentCategory(categoryId);

		return ApiResultResponse.data(
			SuccessCode.CATEGORY_DATA_RESPONSE_SUCCESS,
			result
		);
	}
}
