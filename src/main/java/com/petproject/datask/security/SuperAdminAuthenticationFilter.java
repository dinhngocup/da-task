package com.petproject.datask.security;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.petproject.datask.utils.MessageConstant;
import com.petproject.datask.utils.SecurityConstant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SuperAdminAuthenticationFilter extends OncePerRequestFilter {
	private final RequestMatcher[] includedPatterns = {
			new AntPathRequestMatcher("/users/admin"),
	};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String providedApiKey = request.getHeader(SecurityConstant.AUTHORIZATION_KEY_HEADER);
			byte[] decodedBytes = Base64.getDecoder().decode(providedApiKey);
			String decodedString = new String(decodedBytes);

			if (providedApiKey == null || !SecurityConstant.SUPER_ADMIN_API_KEY.equals(decodedString)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageConstant.INVALID_API_KEY.label);
				return;
			}

		} catch (Exception exception) {
			log.error("Failed to parse API key with exception {} {}.", exception.getMessage(), exception);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageConstant.INVALID_API_KEY.label);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		for (RequestMatcher excludedPattern : includedPatterns) {
			if (excludedPattern.matches(request)) {
				return false;
			}
		}
		return true;
	}

}
