package com.pharmanuman.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.User;

public class AdminDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not found user!");
		}
		CutomUserDetails cutomUserDetails = new CutomUserDetails(user);
		return cutomUserDetails;
	}
}
