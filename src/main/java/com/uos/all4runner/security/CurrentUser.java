package com.uos.all4runner.security;

import java.util.UUID;

import com.uos.all4runner.constant.AccountRole;

public interface CurrentUser {
	UUID getId();
	String getEmail();
	AccountRole getRole();
}
