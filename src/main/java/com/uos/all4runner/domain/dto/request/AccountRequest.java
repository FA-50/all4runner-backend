package com.uos.all4runner.domain.dto.request;

import com.uos.all4runner.constant.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface AccountRequest {
	@Schema(name = "AccountRequest.Create")
	record Create(
		@NotBlank String name,
		@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$") String email,
		@Pattern(regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$") String password,
		@NotBlank Gender gender,
		@NotBlank String addressGu,
		@NotBlank String addressDong,
		@Min(value = 0) @Max(value = 20) Double avgspeed,
		@Min(value = 0) @Max(value = 200) Double weight
		){
	}
}
