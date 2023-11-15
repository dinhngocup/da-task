package com.petproject.datask.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.petproject.datask.utils.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	private final SuperAdminAuthenticationFilter superAdminAuthenticationFilter;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter,
						  SuperAdminAuthenticationFilter superAdminAuthenticationFilter
	) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userDetailsService = userDetailsService;
		this.superAdminAuthenticationFilter = superAdminAuthenticationFilter;
	}

	/**
	 * Configures a custom SecurityFilterChain to manage authentication and
	 * authorization for various endpoints within the application.
	 *
	 * @param http The HttpSecurity object for configuring security settings.
	 * @return A SecurityFilterChain configured to handle authentication and authorization for multiple endpoints
	 * based on
	 * specific rules, including public access, role-based access, and general authentication for other endpoints.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/auth/**", "/users/admin").permitAll()
						.requestMatchers(HttpMethod.GET, "/tasks").permitAll()
						.requestMatchers("/users**").hasRole(Role.ADMIN.name())
						.requestMatchers(HttpMethod.POST, "/tasks").hasRole(Role.ADMIN.name())
						.requestMatchers(HttpMethod.DELETE, "/tasks/**").hasRole(Role.ADMIN.name())
						.requestMatchers(HttpMethod.PUT, "/tasks/**").hasAnyRole(Role.EMPLOYEE.name(), Role.ADMIN.name())
						.anyRequest()
						.authenticated()
				)
				.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(
						jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(superAdminAuthenticationFilter, JWTAuthenticationFilter.class);

		return http.build();
	}

	/**
	 * Configures and returns an AuthenticationProvider bean.
	 * This bean is responsible for authenticating based on UserDetailsService
	 * and password encoder.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
			throws Exception {
		return config.getAuthenticationManager();
	}

}
