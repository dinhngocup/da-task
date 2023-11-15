package com.petproject.datask.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petproject.datask.entity.User;
import com.petproject.datask.repository.UserRepository;
import com.petproject.datask.utils.MessageConstant;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(MessageConstant.INVALID_USERNAME.label);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(user.getRoleId()));

		return new org.springframework.security.core.userdetails.User(username, user.getPswd(), authorities);
	}

}
