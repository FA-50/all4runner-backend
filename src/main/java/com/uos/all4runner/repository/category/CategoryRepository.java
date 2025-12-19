package com.uos.all4runner.repository.category;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.domain.entity.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
