package com.user.userapi.responce;

import javax.servlet.http.HttpServletResponse;

public class BaseClass {

	public ResponseVO success(final ResponseVO responseVO, final Object response, final String message) {

		responseVO.setCode(HttpServletResponse.SC_OK);
		responseVO.setStatus(MessageStore.SUCCESS);
		responseVO.setMessage(message);
		responseVO.setResponse(response);
		return responseVO;
	}
}
