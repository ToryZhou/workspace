/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-23 下午4:40:27 snail
 */
package com.torychow.bat.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table

public class User {
	private Integer nUserId;
	private String sUserName;
	private String sUserEmail;
	private String sUserPassword;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnUserId() {
		return nUserId;
	}
	public void setnUserId(Integer nUserId) {
		this.nUserId = nUserId;
	}
	public String getsUserName() {
		return sUserName;
	}
	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}
	public String getsUserEmail() {
		return sUserEmail;
	}
	public void setsUserEmail(String sUserEmail) {
		this.sUserEmail = sUserEmail;
	}
	public String getsUserPassword() {
		return sUserPassword;
	}
	public void setsUserPassword(String sUserPassword) {
		this.sUserPassword = sUserPassword;
	}
}
