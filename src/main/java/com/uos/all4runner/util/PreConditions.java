package com.uos.all4runner.util;

import com.uos.all4runner.constant.ErrorCode;
import com.uos.all4runner.exception.CustomException;

public final class PreConditions {
	public static void validate(boolean expression, ErrorCode errorCode){
		if (!expression) throw new CustomException(errorCode);
	}
}
