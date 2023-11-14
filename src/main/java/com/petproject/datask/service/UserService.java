package com.petproject.datask.service;

import java.util.List;

import com.petproject.datask.dto.UserDTO;
import com.petproject.datask.entity.User;


public interface UserService {
	List<User> getAll();
	UserDTO getByUsername(String username);
	void add(UserDTO userDTO) throws Exception;
	void delete(String username);
}
