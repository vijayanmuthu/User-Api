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

	static ResponseVO response = new ResponseVO();

	@ExceptionHandler(value = EmailFoundException.class)
	public ResponseEntity<?> exception(Exception exception) {

		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage(exception.getMessage());
		response.setStatus("Error");
		response.setResponse(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<?> ValidationException(Exception exception) {

		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("email is null");
		response.setStatus("error");
		response.setResponse(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
