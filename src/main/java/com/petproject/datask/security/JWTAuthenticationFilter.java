package com.petproject.datask.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.petproject.datask.utils.SecurityConstant;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	@Autowired
	public JWTAuthenticationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String tokenHeader = request.getHeader("Authorization");

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
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		chain.doFilter(request, response);
	}

}
