package com.uos.all4runner.common.response;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uos.all4runner.constant.SuccessCode;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Hidden
public class ApiResultResponse<T> {

	private final String statusCode;

	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	private static <T> ResponseEntity<ApiResultResponse<T>> toResponseEntity(SuccessCode successCode, T data) {
		return ResponseEntity.status(successCode.getStatus())
			.body(
				new ApiResultResponse<T>(
				successCode.getStatus().toString(),
				successCode.getDescription(),
				data
			)
		);
	}

	public static ResponseEntity<ApiResultResponse<Void>> empty(SuccessCode successCode) {
		return ApiResultResponse.toResponseEntity(
			successCode ,
			null
		);
	}

	public static <T> ResponseEntity<ApiResultResponse<T>> data(SuccessCode successCode, T data) {
		return ApiResultResponse.toResponseEntity(
			successCode,
			data
		);
	}

	public static <T> ResponseEntity<ApiResultResponse<Page<T>>> page(SuccessCode successCode, Page<T> page) {
		return ResponseEntity
			.status(successCode.getStatus())
			.body(
				new ApiResultResponse<Page<T>>(
					successCode.getStatus().toString(),
					successCode.getDescription(),
					page
			)
		);
	}
}
