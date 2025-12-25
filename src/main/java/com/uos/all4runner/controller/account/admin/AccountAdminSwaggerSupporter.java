package com.uos.all4runner.controller.account.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.uos.all4runner.common.api.ApiResult;
import com.uos.all4runner.common.page.Paging;
import com.uos.all4runner.controller.common.SwaggerSupoorter;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.security.DefaultCurrentUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AccountAdmin", description = "관리자용 계정 관련 API")
public interface AccountAdminSwaggerSupporter extends SwaggerSupoorter {

	@Operation(
		summary = "ADMIN 계정을 생성합니다.",
		description = "ADMIN 권한의 계정을 생성하는 API"
	)
	ResponseEntity<ApiResult<Void>> createAdmin(
		AccountRequest.Create request
	);

	@Operation(
		summary = "계정을 삭제합니다.",
		description = """
			관리자에 의해 계정을 삭제하는 API \n
			 ( SUPERADMIN 삭제 불가 & ADMIN 계정은 오직 본인만 삭제가능 )
			""",
		parameters = {
			@Parameter(name = "accountId" , description = "삭제를 수행할 계정ID")
		}
	)
	ResponseEntity<ApiResult<Void>> deleteAccount(
		@Parameter(hidden=true) DefaultCurrentUser currentUser,
		UUID accountId
	);

	@Operation(
		summary = "계정을 삭제합니다.",
		description = """
			관리자에 의해 계정을 삭제하는 API \n
			 ( SUPERADMIN 삭제 불가 & ADMIN 계정은 오직 본인만 삭제가능 \n
			 & REMOVED 상태인 계정만 삭제가능 )
			""",
		parameters = {
			@Parameter(name = "accountId" , description = "삭제를 수행할 계정ID")
		}
	)
	ResponseEntity<ApiResult<Void>> deleteAccountPermanently(UUID accountId);

	@Operation(
		summary = "계정을 복구합니다.",
		description = "관리자에 의해 REMOVED 상태인 계정을 ACTIVE 상태로 전환하는 API",
		parameters = {
			@Parameter(name = "accountId" , description = "복구할 계정ID")
		}
	)
	ResponseEntity<ApiResult<Void>> restoreAccount(UUID accountId);

	@Operation(
		summary = "계정의 비밀번호를 수정합니다.",
		description = """
			관리자에 의해 비밀번호를 수정하는 API \n
			( SUPERADMIN 계정에 대해서는 수정 불가능 )
			""",
		parameters = {
			@Parameter(name = "accountId" , description = "변경할 계정ID"),
			@Parameter(name = "newPassword" , description = "새로운 비밀번호")
		}
	)
	ResponseEntity<ApiResult<Void>> updatePassword(
		UUID accountId,
		AccountRequest.UpdatePasswordAdmin request
	);

	@Operation(
		summary = "계정의 정보를 상세조회합니다.",
		description = "관리자에 의해 계정정보를 상세조회하는 API",
		parameters = {
			@Parameter(name = "accountId" , description = "조회할 계정ID")
		}
	)
	ResponseEntity<ApiResult<AccountResponse.Details>> getAccountDetails(
		@Parameter(hidden = true) DefaultCurrentUser currentUser,
		UUID accountId
	);

	@Operation(
		summary = "계정의 정보를 검색합니다.",
		description = "관리자에 의해 계정정보를 검색하는 API",
		parameters = {
			@Parameter(name = "name" , description = "검색할 계정 사용자 이름"),
			@Parameter(name = "paging" , description = "페이지번호 / 페이지에 표현될 데이터수")
		}
	)
	ResponseEntity<ApiResult<Page<AccountResponse.Search>>> getAccountSearches(
		String name,
		Paging paging
	);
}
