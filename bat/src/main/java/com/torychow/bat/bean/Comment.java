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
public class Comment {
	private Integer nCommentId;
	private String sCommentContent;
	private String sCommentTime = CommonUtils.getNowTime();
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnCommentId() {
		return nCommentId;
	}
	public void setnCommentId(Integer nCommentId) {
		this.nCommentId = nCommentId;
	}
	public String getsCommentContent() {
		return sCommentContent;
	}
	public void setsCommentContent(String sCommentContent) {
		this.sCommentContent = sCommentContent;
	}
	public String getsCommentTime() {
		return sCommentTime;
	}
	public void setsCommentTime(String sCommentTime) {
		this.sCommentTime = sCommentTime;
	}
}
