package com.uos.all4runner.common.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.util.Pair;
import com.uos.all4runner.constant.SuccessCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> {

	private final String statusCode;

	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	private static <T> ResponseEntity<ApiResult<T>> toResponseEntity(SuccessCode successCode, T data) {
		return ResponseEntity.status(successCode.getStatus())
			.body(
				new ApiResult<T>(
				successCode.getStatus().toString(),
				successCode.getDescription(),
				data
			)
		);
	}

	public static ResponseEntity<ApiResult<Void>> empty(SuccessCode successCode) {
		return ApiResult.toResponseEntity(
			successCode ,
			null
		);
	}

	public static <T> ResponseEntity<ApiResult<T>> data(SuccessCode successCode, T data) {
		return ApiResult.toResponseEntity(
			successCode,
			data
		);
	}

	public static <T> ResponseEntity<Page<T>> page(SuccessCode successCode, Page<T> page) {
		return ResponseEntity
			.status(successCode.getStatus())
			.body(page);
	}

	public static ResponseEntity<Pair<String,String>> token(SuccessCode successCode, Pair<String,String> tokenPair) {
		return ResponseEntity
			.status(successCode.getStatus())
			.body(tokenPair);
	}
}
