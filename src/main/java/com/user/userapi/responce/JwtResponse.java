package com.user.userapi.responce;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class JwtResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7547622781872923041L;
	private String jwttoken;

	public JwtResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}
}
