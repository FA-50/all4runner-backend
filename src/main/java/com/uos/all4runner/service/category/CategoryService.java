package com.uos.all4runner.service.category;

import com.uos.all4runner.domain.dto.request.CategoryRequest;
import com.uos.all4runner.domain.dto.response.CategoryResponse;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;

public interface CategoryService {

	List<CategoryResponse.Search> searchRootCategory();

	List<CategoryResponse.Search> searchChildCategory(UUID categoryId);

	CategoryResponse.Search searchParentCategory(UUID categoryId);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	void createRootCategory(CategoryRequest.Create request);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	void createChildCategory(CategoryRequest.Create request, UUID categoryId);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	void updateCategory(CategoryRequest.Update request, UUID categoryId);

	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
	void deleteCategory(UUID categoryId);
}
