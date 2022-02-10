package com.user.userapi.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.user.userapi.responce.ResponseVO;

@ControllerAdvice
public class GlobleException {

	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> exception(Exception exception) {
		ResponseVO response = new ResponseVO();
		response.setCode(HttpServletResponse.SC_BAD_REQUEST);
		response.setMessage("error");
		response.setStatus("error");
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

	}

}
