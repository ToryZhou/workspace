/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-23 下午2:21:39 snail
 */
package com.torychow.bat.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torychow.bat.bean.User;
import com.torychow.bat.exception.CodeInfoException;
import com.torychow.bat.service.UserService;
import com.torychow.bat.util.Constants;
import com.torychow.bat.util.ResponseMap;


@Controller
public class LoginController extends BaseController {
	
	@Resource
	private UserService userService;
	@Resource
	private HttpSession httpSession;
	
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "sUserEmail") String sUserEmail,
			@RequestParam(value = "sUserPassword") String sUserPassword)
			throws Exception {
		if(StringUtils.isBlank(sUserEmail)||StringUtils.isBlank(sUserPassword)){
			throw new CodeInfoException("账号或密码不允许为空！");
		}
		User user = userService.getUser(sUserEmail);
		if(null==user){
			throw new CodeInfoException("用户不存在");
		}
		user = userService.getUser(sUserEmail, sUserPassword);
		if(null==user){
			throw new CodeInfoException("密码错误");
		}
		httpSession.setAttribute(Constants.USER, user);
		this.success(response);
	}
	
	@RequestMapping(value = "register")
	public void register(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "sUserName",required=true) String sUserName,
			@RequestParam(value = "sUserEmail",required=true) String sUserEmail,
			@RequestParam(value = "sUserPassword",required=true) String sUserPassword,
			@RequestParam(value = "sUserPasswordConfirm",required=true) String sUserPasswordConfirm
			)throws Exception {
		userService.register(sUserName, sUserEmail, sUserPassword, sUserPasswordConfirm);
		this.success(response);
	}
	
	@RequestMapping(value = "/user/sessionValidate")
	public void sessionValidate(HttpServletRequest request, HttpServletResponse response
			)throws Exception {
		ResponseMap map = new ResponseMap();
		HttpSession session = request.getSession();
		map.put("user", session.getAttribute(Constants.USER));
		String[] include = null;
		String[] exclude = { "sUserPassword"};
		map.success();
		this.getWriter(response).write(map.toJson(include, exclude));
	}
	
	@RequestMapping(value = "/user/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		if (null != session.getAttribute(Constants.USER)) {
			session.removeAttribute(Constants.USER);
		}
		this.success(response);
	}
}
