package com.torychow.bat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.torychow.bat.bean.User;
import com.torychow.bat.exception.CodeInfoException;
import com.torychow.bat.service.BaseService;
import com.torychow.bat.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private BaseService<?> baseService;

	public void register(String sUserName, String sUserEmail,
			String sUserPassword, String sUserPasswordConfirm) {
		if(!sUserPassword.equals(sUserPasswordConfirm)){
			throw new CodeInfoException("两次密码输入不一样！");
		}
		User user=new User();
		user.setsUserName(sUserName);
		user.setsUserEmail(sUserEmail);
		user.setsUserPassword(sUserPassword);
		baseService.saveOrUpdateAndReturnO(user);
	}
}
