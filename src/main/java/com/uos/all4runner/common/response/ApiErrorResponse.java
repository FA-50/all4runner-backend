package com.uos.all4runner.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiErrorResponse {
	private final String code;
	private final String message;
	public static ResponseEntity<ApiErrorResponse> toResponseEntity(
		HttpStatus httpStatus,
		String message
	) {
		return ResponseEntity
			.status(httpStatus)
			.body(
				new ApiErrorResponse(
					httpStatus.toString(),
					message
				)
			);
	}
}
