package com.uos.all4runner.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final String TOKEN_PREFIX = "Bearer ";
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain
	) throws ServletException, IOException{
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null){
			chain.doFilter(request, response);
			return;
		}

		String authToken = authorizationHeader.substring(TOKEN_PREFIX.length());

		if(!jwtService.validate(authToken)){
			chain.doFilter(request, response);
			return;
		}

		Long jwtTokenId = jwtService.parseId(authToken);
		DefaultCurrentUser currentUser = jwtService.getUserDetailFromToken(authToken);
		AuthenticationToken token = new AuthenticationToken(
			currentUser,
			currentUser.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(token);
		chain.doFilter(request, response);
	}
}
