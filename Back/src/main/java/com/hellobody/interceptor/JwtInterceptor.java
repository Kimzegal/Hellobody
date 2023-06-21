package com.hellobody.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hellobody.util.JwtUtil;


@Component
public class JwtInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "login-token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		  if (request.getMethod().equals("OPTIONS"))
		    return true;

		  String token = request.getHeader(HEADER_AUTH);
		  System.out.println("토큰!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + token);

		  JwtUtil.valid(token);

		  return true; // 진행시켜
		}

}
