package com.torychow.bat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.torychow.bat.exception.CodeInfoException;
import com.torychow.bat.util.CodeConstants;
import com.torychow.bat.util.Constants;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Object obj = request.getSession().getAttribute(
				Constants.USER);
		if(null==obj){
			throw new CodeInfoException(CodeConstants.LOGIN_ERROR_CODE,
					CodeConstants.LOGIN_ERROR_CODEINFO);
		}
		return true;
	}
}
