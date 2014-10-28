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
public class Visit {
	private Integer nMessageId;
	private String sVisitAddr;
	private String sVisitHost;
	private String sVisitTime = CommonUtils.getNowTime();
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnMessageId() {
		return nMessageId;
	}
	public void setnMessageId(Integer nMessageId) {
		this.nMessageId = nMessageId;
	}
	public String getsVisitAddr() {
		return sVisitAddr;
	}
	public void setsVisitAddr(String sVisitAddr) {
		this.sVisitAddr = sVisitAddr;
	}
	public String getsVisitHost() {
		return sVisitHost;
	}
	public void setsVisitHost(String sVisitHost) {
		this.sVisitHost = sVisitHost;
	}
	public String getsVisitTime() {
		return sVisitTime;
	}
	public void setsVisitTime(String sVisitTime) {
		this.sVisitTime = sVisitTime;
	}
}
