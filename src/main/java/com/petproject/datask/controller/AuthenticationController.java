package com.petproject.datask.controller;

import static com.petproject.datask.utils.SecurityConstant.SECRET_KEY_STRING;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petproject.datask.dto.AuthenticationDTO;
import com.petproject.datask.dto.UserDTO;
import com.petproject.datask.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Object login(@RequestBody AuthenticationDTO authenDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenDTO.getUsername(), authenDTO.getPswd()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDTO user = userService.getByUsername(authenDTO.getUsername());
			user.setAccessToken(generateToken(authenDTO.getUsername()));
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Failed to handling request {}", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String generateToken(String username) {
		Date dateNow = new Date();
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(dateNow)
				.setExpiration(new Date(dateNow.getTime() + 864000000L))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY_STRING)
				.compact();
	}
}
