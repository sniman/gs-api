
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import java.util.ArrayList;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gs.api.db.entity.AuthUser;
import com.gs.api.db.repositories.AuthUserRepository;



@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private AuthUserRepository userRepository;

	
	@Override
	@Qualifier(value = "boTransactionManager")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser usr=userRepository.findByUsername(username);
		String dbuser=usr.getUsername();
		String dbpassword=usr.getPassword();
			
		
		if (dbuser.equals(username)) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(dbpassword);
			return new User(dbuser, encodedPassword,
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	




}