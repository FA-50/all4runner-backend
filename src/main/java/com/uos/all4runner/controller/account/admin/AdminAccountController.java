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

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.common.request.Paging;
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
public class AdminAccountController implements AdminAccountSwaggerSupporter {
	private final AccountService accountService;

	@Override
	@PostMapping
	public ResponseEntity<ApiResultResponse<Void>> createAdmin(
		@RequestBody @Valid AccountRequest.Create request
	) {
		accountService.createAdmin(request);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_CREATE_SUCCESS
		);
	}

	@Override
	@DeleteMapping("/{accountId}")
	public ResponseEntity<ApiResultResponse<Void>> deleteAccount(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable UUID accountId
	) {
		accountService.deleteAccount(
			currentUser.getId(),
			accountId
		);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_DELETE_SUCCESS
		);
	}

	@Override
	@DeleteMapping("/{accountId}/hard")
	public ResponseEntity<ApiResultResponse<Void>> deleteAccountPermanently(
		@PathVariable	UUID accountId
	) {
		accountService.deleteAccountPermanently(accountId);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_DELETE_SUCCESS
		);
	}

	@Override
	@PatchMapping("/{accountId}/restore")
	public ResponseEntity<ApiResultResponse<Void>> restoreAccount(
		@PathVariable	UUID accountId
	) {
		accountService.restoreAccount(accountId);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_RESTORE_SUCCESS
		);
	}

	@Override
	@PatchMapping("/{accountId}/password")
	public ResponseEntity<ApiResultResponse<Void>> updatePassword(
		@PathVariable	UUID accountId,
		@RequestBody @Valid AccountRequest.UpdatePasswordAdmin request
	) {
		accountService.updatePasswordByAdmin(
			accountId,
			request.newPassword()
		);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_PASSWORD_UPDATE_SUCCESS
		);
	}

	@Override
	@GetMapping("/{accountId}")
	public ResponseEntity<ApiResultResponse<AccountResponse.Details>> getAccountDetails(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable UUID accountId
	) {
		return ApiResultResponse.data(
			SuccessCode.ACCOUNT_DATA_RESPONSE_SUCCESS,
			accountService.getAccountDetails(
				currentUser.getId(),
				accountId
			)
		);
	}

	@Override
	@GetMapping
	public ResponseEntity<ApiResultResponse<Page<AccountResponse.Search>>> getAccountSearches(
		@RequestParam(required = false) String name,
		@ModelAttribute @Valid Paging paging
	) {
		return ApiResultResponse.page(
			SuccessCode.ACCOUNT_DATA_RESPONSE_SUCCESS,
			accountService.searchAccounts(
				name,
				paging.getPageable()
			)
		);
	}
}
