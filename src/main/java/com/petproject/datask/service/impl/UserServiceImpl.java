package com.petproject.datask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.petproject.datask.dto.UserDTO;
import com.petproject.datask.entity.User;
import com.petproject.datask.repository.UserRepository;
import com.petproject.datask.service.UserService;
import com.petproject.datask.utils.MessageConstant;
import com.petproject.datask.utils.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Get All Users.
	 *
	 * @return List<User>
	 */
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	/**
	 * Get User by username.
	 *
	 * @return UserDTO
	 */
	@Override
	public UserDTO getByUsername(String username) {
		User user = userRepository.findByUsername(username);

		return UserMapper.mapEntityToDto(user);
	}

	/**
	 * Add New User.
	 *
	 * @param userDTO The new data for the user.
	 */
	@Override
	public void add(UserDTO userDTO) throws Exception {
		User user = UserMapper.mapDtoToEntity(userDTO);

		User entityHasSameEmail = userRepository.findByUsername(user.getUsername());
		if (entityHasSameEmail != null) {
			throw new Exception(MessageConstant.USERNAME_EXISTED.label);
		}
		String hashPassword = BCrypt.hashpw(user.getPswd(), BCrypt.gensalt());
		user.setPswd(hashPassword);
		userRepository.save(user);
	}

	/**
	 * Delete Current User.
	 */
	@Override
	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

}
