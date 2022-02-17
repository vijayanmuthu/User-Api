package com.user.userapi.exception;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.userapi.responce.ResponseVO;

@ControllerAdvice
public class GlobalException {

	

	@ExceptionHandler(value = EmailFoundException.class)
	public ResponseEntity<?> exception(Exception exception) {
		
		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage(exception.getMessage());
		response.setStatus("Error");
		response.setResponse(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<?> ValidationException(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("email is null");
		response.setStatus("error");
		response.setResponse(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(value = UsernameNotFoundError.class)
	public ResponseEntity<?> UsernameNotFoundError(Exception exception) {

		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("Username Not Found");
		response.setStatus("error");
		response.setResponse("");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
}
