/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-24 上午11:15:51 snail
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
public class Action {
	private Integer nActionId;
	private String sActionName;
	private String sActionDescription;
	private String sActionPath;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnActionId() {
		return nActionId;
	}
	public void setnActionId(Integer nActionId) {
		this.nActionId = nActionId;
	}
	public String getsActionName() {
		return sActionName;
	}
	public void setsActionName(String sActionName) {
		this.sActionName = sActionName;
	}
	public String getsActionDescription() {
		return sActionDescription;
	}
	public void setsActionDescription(String sActionDescription) {
		this.sActionDescription = sActionDescription;
	}
	public String getsActionPath() {
		return sActionPath;
	}
	public void setsActionPath(String sActionPath) {
		this.sActionPath = sActionPath;
	}
	
}
