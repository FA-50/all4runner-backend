package com.uos.all4runner.domain.dto.request;

import org.hibernate.validator.constraints.Range;

import com.uos.all4runner.constant.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public interface AccountRequest {
	@Schema(name = "AccountRequest.Create")
	record Create(
		@NotBlank String name,
		@Email String email,
		@Pattern(
			regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
			message = "비밀번호는 최소 8자 이상이면서 특수문자를 하나 포함해야합니다."
		) String password,
		@NotNull Gender gender,
		@NotBlank String addressGu,
		@NotBlank String addressDong,
		@Range(min = 1, max = 30, message = "1 ~ 30 km/h 범위 이내 속도를 작성해야합니다.") Double avgspeed,
		@Range(min = 1, max = 150, message = "1 ~ 150 kg 범위 이내 체중을 작성해야합니다.") Double weight
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
		@NotBlank String currentPassword,
		@Pattern(
			regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
			message = "비밀번호는 최소 8자 이상이면서 특수문자를 하나 포함해야합니다."
		) String newPassword
	){
	}

	@Schema(name = "AccountRequest.UpdatePasswordAdmin")
	record UpdatePasswordAdmin(
		@Pattern(
			regexp = "^(?=.{8,}$)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$",
			message = "비밀번호는 최소 8자 이상이면서 특수문자를 하나 포함해야합니다."
		) String newPassword
	){
	}
}
