package com.user.userapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.user.userapi.Entity.Userdetail;
import com.user.userapi.exception.UsernameNotFoundError;
import com.user.userapi.repo.UserdetailRepo;
@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserdetailRepo userdetailRepo;
	
	

	@Override
	public UserDetails loadUserByUsername(String username){
		Userdetail user = userdetailRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundError("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
}
