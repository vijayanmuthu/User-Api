package com.user.userapi.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.userapi.responce.ResponseVO;

@ControllerAdvice
public class GlobalException {
	
	static ResponseVO response = new ResponseVO();

	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> exception(Exception exception) {

		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("email alreay exist");
		response.setStatus("error");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<?> NullPointer(Exception exception) {

		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("email not Null");
		response.setStatus("error");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
