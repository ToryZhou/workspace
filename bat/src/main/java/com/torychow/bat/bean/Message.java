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

import com.torychow.bat.util.CommonUtils;

@Entity
@Table
public class Message {
	private Integer nMessageId;
	private String sMessageContent;
	private String sMessageTime=CommonUtils.getNowTime();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnMessageId() {
		return nMessageId;
	}
	public void setnMessageId(Integer nMessageId) {
		this.nMessageId = nMessageId;
	}
	public String getsMessageContent() {
		return sMessageContent;
	}
	public void setsMessageContent(String sMessageContent) {
		this.sMessageContent = sMessageContent;
	}
	public String getsMessageTime() {
		return sMessageTime;
	}
	public void setsMessageTime(String sMessageTime) {
		this.sMessageTime = sMessageTime;
	}
}
