package com.tjoeun.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint  implements  AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
											AuthenticationException authException) throws IOException, ServletException {
		
		//인증받지 않은 유저가 접근 했을 때 
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

		
	}

}
