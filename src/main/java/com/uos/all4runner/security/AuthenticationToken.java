package com.uos.all4runner.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationToken extends AbstractAuthenticationToken {
	private final CurrentUser currentUser;
	public AuthenticationToken(
		CurrentUser currentUser,
		Collection<? extends GrantedAuthority> authorities
	) {
		super(authorities);
		super.setAuthenticated(true);
		this.currentUser = currentUser;
	}
	@Override
	public Object getCredentials() {
		return currentUser.getId();
	}
	@Override
	public Object getPrincipal() {
		return currentUser;
	}
}
