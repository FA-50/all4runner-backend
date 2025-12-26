package com.uos.all4runner.common.request;

import org.springframework.data.domain.Pageable;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Min;

@Hidden
public record Paging(
	@Min(value = 1, message = "Page 번호는 1 이하이어야합니다.") int page,
	@Range(min = 1, max = 20, message = "size는 1 이상 20 이하이어야합니다.") int size
) {
	public Pageable getPageable() { return PageRequest.of(page - 1, size); }
}
