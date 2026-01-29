package com.uos.all4runner.repository.refreshtoken;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.exception.CustomException;
import com.uos.all4runner.security.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	default RefreshToken findByRefreshTokenByIdOrThrow(String refreshToken) {
		return findById(refreshToken).orElseThrow(()->new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
	}
}
