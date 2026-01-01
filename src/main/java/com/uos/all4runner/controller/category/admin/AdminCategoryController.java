package com.uos.all4runner.controller.category.admin;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.CategoryRequest;
import com.uos.all4runner.service.category.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController implements AdminCategorySwaggerSupoorter {

	private final CategoryService categoryService;

	@Override
	@PostMapping
	public ResponseEntity<ApiResultResponse<Void>> createRootCategory(
		@RequestBody @Valid CategoryRequest.Create request
	) {
		categoryService.createRootCategory(request);

		return ApiResultResponse.empty(SuccessCode.CATEGORY_CREATE_SUCCESS);
	}

	@Override
	@PostMapping("/{categoryId}")
	public ResponseEntity<ApiResultResponse<Void>> createChildCategory(
		@PathVariable UUID categoryId,
		@RequestBody @Valid	CategoryRequest.Create request
	) {
		categoryService.createChildCategory(request, categoryId);

		return ApiResultResponse.empty(SuccessCode.CATEGORY_CREATE_SUCCESS);
	}

	@Override
	@PatchMapping("/{categoryId}")
	public ResponseEntity<ApiResultResponse<Void>> updateCategory(
		@PathVariable	UUID categoryId,
		@RequestBody @Valid	CategoryRequest.Update request
	) {
		categoryService.updateCategory(request, categoryId);

		return ApiResultResponse.empty(SuccessCode.CATEGORY_UPDATE_SUCCESS);
	}

	@Override
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResultResponse<Void>> deleteCategory(
		@PathVariable	UUID categoryId
	) {
		categoryService.deleteCategory(categoryId);

		return ApiResultResponse.empty(SuccessCode.CATEGORY_DELETE_SUCCESS);
	}
}
