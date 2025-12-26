package com.uos.all4runner.common.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.uos.all4runner.common.response.ApiErrorResponse;
import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.exception.CustomException;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@Hidden
@Slf4j
@RestControllerAdvice
public class ApiAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return ApiErrorResponse.toResponseEntity(
			ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
			ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
		);
	}
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException e) {
		return ApiErrorResponse.toResponseEntity(
			e.getStatus(),
			e.getMessage()
		);
	}
}
