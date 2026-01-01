package com.uos.all4runner.repository.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.exception.CustomException;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByName(String name);

	default Category findByNameOrThrow(String name){
		return findByName(name).orElseThrow(
			()-> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
		);
	}

	@Query("""
		SELECT c
			FROM Category c
			WHERE c.parentCategory is null
	""")
	List<Category> findRootCateories();

	@Query("""
		SELECT c.categories
			FROM Category c
			WHERE c.id = ?1
	""")
	List<Category> findChildCateories(UUID categoryId);

	@Query("""
		SELECT c.parentCategory
			FROM Category c
			WHERE c.id = ?1
	""")
	Category findParentCateories(UUID categoryId);
}
