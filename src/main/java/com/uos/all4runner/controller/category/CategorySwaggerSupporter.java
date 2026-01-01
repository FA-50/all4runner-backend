package com.uos.all4runner.controller.category;

import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.response.CategoryResponse;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category", description = "카테고리 관련 Api")
public interface CategorySwaggerSupporter extends SwaggerSupoorter {

	@Operation(
		summary = "상위 카테고리를 검색",
		description = "최상위 카테고리 목록을 반환하는 API"
	)
	ResponseEntity<ApiResultResponse<List<CategoryResponse.Search>>> getRootCategory();

	@Operation(
		summary = "자식 카테고리를 검색",
		description = "특정 카테고리의 자식 카테고리 목록을 반환하는 API",
		parameters = {
			@Parameter(name = "categoryId" , description = "카테고리ID")
		}
	)
	ResponseEntity<ApiResultResponse<List<CategoryResponse.Search>>> getChildCategory(UUID categoryId);

	@Operation(
		summary = "부모 카테고리를 검색",
		description = "특정 카테고리의 부모 카테고리를 반환하는 API",
		parameters = {
			@Parameter(name = "categoryId" , description = "카테고리ID")
		}
	)
	ResponseEntity<ApiResultResponse<CategoryResponse.Search>> getParentCategory(UUID categoryId);
}



