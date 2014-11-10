package com.torychow.bat.service;

import com.torychow.bat.bean.User;




public interface UserService {
	public void register(String sUserName, String sUserEmail,
			String sUserPassword, String sUserPasswordConfirm);
	public User getUser(String sUserEmail);
	public User getUser(String sUserEmail,String sUserPassword);
}
