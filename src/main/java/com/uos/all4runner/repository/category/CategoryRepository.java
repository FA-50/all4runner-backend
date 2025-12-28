package com.uos.all4runner.repository.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.domain.entity.category.Category;
import com.uos.all4runner.exception.CustomException;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
	Optional<Category> findByName(String name);

	default Category findByNameOrThrow(String name){
		return findByName(name).orElseThrow(
			()-> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
		);
	}
}
