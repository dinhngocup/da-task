package com.petproject.datask.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petproject.datask.dto.UserDTO;
import com.petproject.datask.entity.User;
import com.petproject.datask.service.UserService;
import com.petproject.datask.utils.MessageConstant;
import com.petproject.datask.utils.ResponseHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public Object getAll() {
		try {
			List<User> userList = userService.getAll();
			return ResponseHandler.generateResponse(MessageConstant.SUCCESS.label, HttpStatus.OK, userList);
		} catch (Exception e) {
			log.error("Failed to get all users with exception {} {}.", e.getMessage(), e);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/admin")
	public Object addAdmin(@RequestBody UserDTO user) {
		try {
			userService.add(user);
			return ResponseHandler.generateResponse(MessageConstant.CREATED_SUCCESSFULLY.label, HttpStatus.CREATED);
		} catch (Exception e) {
			if (!e.getMessage().equals(MessageConstant.USERNAME_EXISTED.label)) {
				log.error("Username of super admin already existed {}.", user.getUsername());
				return ResponseHandler.generateResponse(MessageConstant.USERNAME_EXISTED.label,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			log.error("Failed to add super admin {} with exception {} {}.", user.getUsername(), e.getMessage(), e);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public Object add(@RequestBody UserDTO user) {
		try {
			userService.add(user);
			return ResponseHandler.generateResponse(MessageConstant.CREATED_SUCCESSFULLY.label, HttpStatus.CREATED);
		} catch (Exception e) {
			if (!e.getMessage().equals(MessageConstant.USERNAME_EXISTED.label)) {
				log.error("Username already existed {}.", user.getUsername());
				return ResponseHandler.generateResponse(MessageConstant.USERNAME_EXISTED.label,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			log.error("Failed to add user {} with exception {} {}.", user.getUsername(), e.getMessage(), e);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{username}")
	public Object delete(@PathVariable String username) {
		try {
			userService.delete(username);
			return ResponseHandler.generateResponse(MessageConstant.DELETED_SUCCESSFULLY.label, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Failed to delete user {} with exception {} {}.", username, e.getMessage(), e);
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
