package com.uos.all4runner.controller.category.admin;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.domain.dto.request.CategoryRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AdminCategory", description = "관리자용 카테고리 관련 Api")
public interface AdminCategorySwaggerSupoorter {
	@Operation(
		summary = "최상위 카테고리 생성",
		description = "최상위 카테고리를 생성하는 API"
	)
	ResponseEntity<ApiResultResponse<Void>> createRootCategory(CategoryRequest.Create request);

	@Operation(
		summary = "자식 카테고리 생성",
		description = "특정 카테고리의 자식카테고리를 생성하는 API",
		parameters = {
			@Parameter(name = "categoryId" , description = "카테고리ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> createChildCategory(
		UUID categoryId,
		@Parameter(hidden = true)	CategoryRequest.Create request
	);

	@Operation(
		summary = "카테고리 수정",
		description = "특정 카테고리를 수정하는 API",
		parameters = {
			@Parameter(name = "categoryId" , description = "카테고리ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> updateCategory(
		UUID categoryId,
		@Parameter(hidden = true) CategoryRequest.Update request
	);

	@Operation(
		summary = "카테고리 삭제",
		description = "특정 카테고리를 삭제하는 API",
		parameters = {
			@Parameter(name = "categoryId" , description = "카테고리ID")
		}
	)
	ResponseEntity<ApiResultResponse<Void>> deleteCategory(UUID categoryId);

}
