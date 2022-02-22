package com.user.userapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user.userapi.config.JwtRequest;
import com.user.userapi.config.JwtTokenUtil;
import com.user.userapi.exception.PasswordNotFoundException;
import com.user.userapi.repo.UserdetailRepo;
import com.user.userapi.responce.BaseClass;
import com.user.userapi.responce.JwtResponse;
import com.user.userapi.responce.MessageStore;
import com.user.userapi.responce.ResponseVO;
import com.user.userapi.service.JwtUserDetailsService;

@RestController
public class JwtAuthenticationController extends BaseClass {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	UserdetailRepo userdetailRepo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseVO createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		ResponseVO responseVO = new ResponseVO();
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		return super.success(responseVO, new JwtResponse(token), MessageStore.GENERATE);
	}

	private void authenticate(String username, String password) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED");
		} catch (BadCredentialsException e) {
			throw new PasswordNotFoundException("Password incorrcect");
		}
	}

}
