package com.petproject.datask.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.petproject.datask.utils.SecurityConstant;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private final RequestMatcher[] excludedPatterns = {
			new AntPathRequestMatcher("/auth/**"),
			new AntPathRequestMatcher("/users/admin"),
			new AntPathRequestMatcher("/tasks", "GET")
	};
	private final UserDetailsService userDetailsService;

	@Autowired
	public JWTAuthenticationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		for (RequestMatcher excludedPattern : excludedPatterns) {
			if (excludedPattern.matches(request)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String tokenHeader = request.getHeader(SecurityConstant.AUTHORIZATION_KEY_HEADER);

		if (tokenHeader != null && tokenHeader.startsWith(SecurityConstant.BEARER_PRFIX)) {
			try {
				String token = tokenHeader.replace(SecurityConstant.BEARER_PRFIX, "");
				String email = Jwts
						.parser()
						.setSigningKey(SecurityConstant.SECRET_KEY_STRING)
						.parseClaimsJws(token)
						.getBody()
						.getSubject();

				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				log.error("Failed to parse JWT token with exception {} {}.", e.getMessage(), e);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		chain.doFilter(request, response);
	}

}
