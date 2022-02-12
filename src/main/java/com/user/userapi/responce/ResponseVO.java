package com.user.userapi.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVO {
	private int code;
	private String status;
	private String message;
	private Object response;
}
