package com.uos.all4runner.controller.account.admin;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.api.ApiResult;
import com.uos.all4runner.common.page.Paging;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.service.account.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/accounts")
@RequiredArgsConstructor
public class AccountAdminController implements AccountAdminSwaggerSupporter{
	private final AccountService accountService;

	@Override
	@PostMapping
	public ResponseEntity<ApiResult<Void>> createAdmin(
		@RequestBody @Valid AccountRequest.Create request
	) {
		accountService.createAdmin(request);

		return ApiResult.empty(
			SuccessCode.ACCOUNT_CREATE_SUCCESS
		);
	}

	@Override
	@DeleteMapping("/{accountId}")
	public ResponseEntity<ApiResult<Void>> deleteAccount(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable UUID accountId
	) {
		accountService.deleteAccount(
			currentUser.getId(),
			accountId
		);

		return ApiResult.empty(
			SuccessCode.ACCOUNT_DELETE_SUCCESS
		);
	}

	@Override
	@DeleteMapping("/{accountId}/hard-delete")
	public ResponseEntity<ApiResult<Void>> deleteAccountPermanently(
		@PathVariable	UUID accountId
	) {
		accountService.deleteAccountPermanently(accountId);

		return ApiResult.empty(
			SuccessCode.ACCOUNT_DELETE_SUCCESS
		);
	}

	@Override
	@PatchMapping("/{accountId}/activate")
	public ResponseEntity<ApiResult<Void>> restoreAccount(
		@PathVariable	UUID accountId
	) {
		accountService.restoreAccount(accountId);

		return ApiResult.empty(
			SuccessCode.ACCOUNT_RESTORE_SUCCESS
		);
	}

	@Override
	@PatchMapping("/{accountId}/password")
	public ResponseEntity<ApiResult<Void>> updatePassword(
		@PathVariable	UUID accountId,
		@RequestBody @Valid AccountRequest.UpdatePasswordAdmin request
	) {
		accountService.updatePasswordByAdmin(
			accountId,
			request.newPassword()
		);

		return ApiResult.empty(
			SuccessCode.ACCOUNT_PASSWORD_UPDATE_SUCCESS
		);
	}

	@Override
	@GetMapping("/{accountId}")
	public ResponseEntity<ApiResult<AccountResponse.Details>> getAccountDetails(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable UUID accountId
	) {
		return ApiResult.data(
			SuccessCode.ACCOUNT_DATA_RESPONSE_SUCCESS,
			accountService.getAccountDetails(
				currentUser.getId(),
				accountId
			)
		);
	}

	@Override
	@GetMapping
	public ResponseEntity<ApiResult<Page<AccountResponse.Search>>> getAccountSearches(
		@RequestParam(required = false) String name,
		@ModelAttribute @Valid Paging paging
	) {
		return ApiResult.page(
			SuccessCode.ACCOUNT_DATA_RESPONSE_SUCCESS,
			accountService.searchAccounts(
				name,
				paging.getPageable()
			)
		);
	}
}
