package com.uos.all4runner.controller.account;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.all4runner.common.response.ApiResultResponse;
import com.uos.all4runner.constant.SuccessCode;
import com.uos.all4runner.domain.dto.request.AccountRequest;
import com.uos.all4runner.domain.dto.response.AccountResponse;
import com.uos.all4runner.security.DefaultCurrentUser;
import com.uos.all4runner.service.account.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController implements AccountSwaggerSupporter {
	private final AccountService accountService;

	@Override
	@PostMapping
	public ResponseEntity<ApiResultResponse<Void>> createMember(
		@RequestBody @Valid AccountRequest.Create request
	) {
		accountService.createMember(request);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_CREATE_SUCCESS
		);
	}

	@Override
	@DeleteMapping
	public ResponseEntity<ApiResultResponse<Void>> deleteAccount(
	 @AuthenticationPrincipal DefaultCurrentUser currentUser
	) {
		accountService.deleteAccount(
			currentUser.getId(),
			currentUser.getId()
		);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_DELETE_SUCCESS
		);
	}

	@Override
	@PutMapping
	public ResponseEntity<ApiResultResponse<Void>> updateAccount(
	 	@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@PathVariable UUID accountId,
		@RequestBody @Valid AccountRequest.Update request
	) {
		accountService.updateAccount(
			currentUser.getId(),
			accountId,
			request
		);

		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_UPDATE_SUCCESS
		);
	}

	@Override
	@PatchMapping("/password")
	public ResponseEntity<ApiResultResponse<Void>> updatePassword(
		@AuthenticationPrincipal DefaultCurrentUser currentUser,
		@RequestBody @Valid	AccountRequest.UpdatePassword request
	) {
		accountService.updatePassword(
			currentUser.getId(),
			request
		);
		return ApiResultResponse.empty(
			SuccessCode.ACCOUNT_PASSWORD_UPDATE_SUCCESS
		);
	}

	@Override
	@GetMapping
	public ResponseEntity<ApiResultResponse<AccountResponse.Details>> getAccountDetails(
		@AuthenticationPrincipal DefaultCurrentUser currentUser
	) {
		return ApiResultResponse.data(
			SuccessCode.ACCOUNT_DATA_RESPONSE_SUCCESS,
			accountService.getAccountDetails(
				currentUser.getId(),
				currentUser.getId()
			)
		);
	}

}
