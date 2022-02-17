package com.user.userapi.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class JwtRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	
	
	

	public JwtRequest(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}
}

	


