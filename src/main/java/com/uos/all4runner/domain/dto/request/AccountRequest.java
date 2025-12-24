package com.uos.all4runner.domain.dto.request;

import com.uos.all4runner.constant.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public interface AccountRequest {
	@Schema(name = "AccountRequest.Create")
	record Create(
		@NotBlank String name,
		@Email String email,
		@Pattern(regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$") String password,
		@NotNull Gender gender,
		@NotBlank String addressGu,
		@NotBlank String addressDong,
		@NotNull @Min(value = 0) @Max(value = 20) Double avgspeed,
		@NotNull @Min(value = 0) @Max(value = 200) Double weight
		){
	}
	@Schema(name = "AccountRequest.Login")
	record Login(
		@NotBlank String email,
		@NotBlank String password
	){
	}

	@Schema(name = "AccountRequest.Update")
	record Update(
		String name,
		Gender gender,
		String addressGu,
		String addressDong,
		Double avgspeed,
		Double weight
	){
	}

	@Schema(name = "AccountRequest.UpdatePassword")
	record UpdatePassword(
		@Pattern(regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$") String currentPassword,
		@Pattern(regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$") String newPassword
	){
	}
}
