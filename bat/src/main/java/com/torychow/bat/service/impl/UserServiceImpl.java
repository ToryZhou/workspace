package com.torychow.bat.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if (!sUserPassword.equals(sUserPasswordConfirm)) {
			throw new CodeInfoException("两次密码输入不一样！");
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(sUserEmail);
		if(!matcher.matches()){
			throw new CodeInfoException("邮箱格式不正确！");
		}
		if(null!=getUser(sUserEmail)){
			throw new CodeInfoException("邮箱已注册！");
		}
		User user = new User();
		user.setsUserName(sUserName);
		user.setsUserEmail(sUserEmail);
		user.setsUserPassword(sUserPassword);
		baseService.saveOrUpdateAndReturnO(user);
	}
	
	public User getUser(String sUserEmail){
		String jpql="select o from User o where o.sUserEmail=?";
		 Object singleResultByJpql = baseService.getSingleResultByJpql(jpql, new Object[]{sUserEmail});
		 if(null!=singleResultByJpql){
			 return (User) singleResultByJpql;
		 }
		 return null;
	}
}
