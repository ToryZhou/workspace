/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-23 下午2:21:39 snail
 */
package com.torychow.bat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torychow.bat.service.UserService;


@Controller
public class LoginController extends BaseController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "password") String password)
			throws Exception {
		System.out.println("haha");
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
}
