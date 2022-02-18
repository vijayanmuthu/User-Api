package com.user.userapi.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.userapi.responce.ResponseVO;

@ControllerAdvice
public class GlobalException {

	// EmailFoundException
	@ExceptionHandler(value = EmailFoundException.class)
	public ResponseEntity<?> exception(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage(exception.getMessage());
		response.setStatus("Error");
		response.setResponse(response.getResponse());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	// EmailValidationException
	@ExceptionHandler(value = EmailValidationException.class)
	public ResponseEntity<?> EmailValidationException(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("email is null");
		response.setStatus("error");
		response.setResponse(response.getResponse());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	// UsernameNotFoundError
	@ExceptionHandler(value = UsernameNotFoundError.class)
	public ResponseEntity<?> UsernameNotFoundError(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("Username Not Found");
		response.setStatus("error");
		response.setResponse(response.getResponse());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	// PasswordNotFoundException
	@ExceptionHandler(value = PasswordNotFoundException.class)
	public ResponseEntity<?> PasswordNotFoundException(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("Password Incorrect");
		response.setStatus("error");
		response.setResponse(response.getResponse());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
}
