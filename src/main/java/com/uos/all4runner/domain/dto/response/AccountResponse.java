package com.uos.all4runner.domain.dto.response;

import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;
import com.uos.all4runner.constant.AccountRole;
import com.uos.all4runner.constant.AccountStatus;
import com.uos.all4runner.constant.Gender;

import io.swagger.v3.oas.annotations.media.Schema;

public interface AccountResponse {
	@Schema(name = "AccountResponse.Search")
	record Search(
		UUID accountId,
		String name,
		AccountRole role,
		AccountStatus status
	){
		@QueryProjection
		public Search{
		}
	}

	@Schema(name = "AccountResponse.Details")
	record Details(
		UUID accountId,
		String name,
		String email,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgSpeed,
		Double weight
	){
		@QueryProjection
		public Details{
		}
	}

	@Schema(name = "AccountResponse.Login")
	record Login(
		String accessToken,
		String refreshToken
	){
	}
}
