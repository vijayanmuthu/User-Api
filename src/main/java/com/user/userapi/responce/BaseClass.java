package com.user.userapi.responce;

import javax.servlet.http.HttpServletResponse;

public class BaseClass {
	public ResponseVO success(final ResponseVO responseVo, final Object response, final String message) {
		responseVo.setCode(HttpServletResponse.SC_OK);
		responseVo.setStatus(MessageStore.SUCCESS);
		responseVo.setMessage(message);
		responseVo.setResponse(response);
		return responseVo;

	}
}
