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


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private final String USERNAME_EXISTED_ERROR = "Username already existed";

	@GetMapping
	public Object getAll() {
		try {
			List<User> userList = userService.getAll();
			return new ResponseEntity<>(userList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public Object add(@RequestBody UserDTO user) {
		try {
			userService.add(user);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			// todo: generic exception
			if (!e.getMessage().equals(USERNAME_EXISTED_ERROR)) {
				return new ResponseEntity<>(USERNAME_EXISTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{username}")
	public Object delete(@PathVariable String username) {
		try {
			userService.delete(username);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e + " " + e.getMessage());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
