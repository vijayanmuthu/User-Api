package com.user.userapi.exception;

public class UsernameNotFoundError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsernameNotFoundError(String message) {
		super(message);
	}

}
