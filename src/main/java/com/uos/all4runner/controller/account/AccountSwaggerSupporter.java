package com.uos.all4runner.controller.account;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.uos.all4runner.common.api.ApiResult;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account", description = "일반 사용자용 계정 관련 API")
public interface AccountSwaggerSupporter {

	@Operation(
		summary = "MEMBER 계정을 생성합니다.",
		description = "MEMBER 권한의 계정을 생성하는 API"
	)
	ResponseEntity<ApiResult<Void>> createMember(AccountRequest.Create request);

	@Operation(
		summary = "계정을 삭제합니다.",
		description = "자신의 계정을 삭제하는 API ( SUPERADMIN 삭제 불가 )"
	)
	ResponseEntity<ApiResult<Void>> deleteAccount(
		@Parameter(hidden=true) DefaultCurrentUser currentUser
	);

	@Operation(
		summary = "계정정보를 수정합니다.",
		description = "자신의 계정정보를 수정하는 API",
		parameters = {
			@Parameter(name = "accountId" , description = "수정할 계정ID")
		}
	)
	ResponseEntity<ApiResult<Void>> updateAccount(
		@Parameter(hidden=true) DefaultCurrentUser currentUser,
		UUID accountId,
		AccountRequest.Update request
	);

	@Operation(
		summary = "계정의 비밀번호를 수정합니다.",
		description = "자신의 비밀번호를 수정하는 API"
	)
	ResponseEntity<ApiResult<Void>> updatePassword(
		@Parameter(hidden = true) DefaultCurrentUser currentUser,
		AccountRequest.UpdatePassword request
	);

	@Operation(
		summary = "계정의 정보를 상세조회합니다.",
		description = "자신의 계정정보를 상세조회하는 API"
	)
	ResponseEntity<ApiResult<AccountResponse.Details>> getAccountDetails(
		@Parameter(hidden = true) DefaultCurrentUser currentUser
	);
}
