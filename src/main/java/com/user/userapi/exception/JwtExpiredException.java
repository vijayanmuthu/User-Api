package com.user.userapi.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.user.userapi.responce.ResponseVO;
@Component
public class JwtExpiredException implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		 final Object expired = request.getAttribute("expired");
	        System.out.println(expired);
	        if (expired!=null){
	        	ResponseVO responseVO = new ResponseVO();
	        	responseVO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
	        	responseVO.setMessage("Token Expird");
	        	responseVO.setStatus("error");
	        	responseVO.setResponse("");
	        }else{
	        	ResponseVO responseVO = new ResponseVO();
	        	responseVO.setCode(HttpServletResponse.SC_UNAUTHORIZED);
	        	responseVO.setMessage("Access Denied");
	        	responseVO.setStatus("error");
	        	responseVO.setResponse("");
	        }
	}

}
