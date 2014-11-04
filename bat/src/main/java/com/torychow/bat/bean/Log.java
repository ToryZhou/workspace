/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午1:27:12 snail
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
public class Log {
	private Integer nLogId;
	private String sHostIp;
	private String sPort;
	private Integer nStatus;//0-正常，1-预警，2-异常，3-客户端已断开,4-新建(还未进行检测)
	private String sTime=CommonUtils.getNowTime();

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnLogId() {
		return nLogId;
	}

	public void setnLogId(Integer nLogId) {
		this.nLogId = nLogId;
	}

	public String getsHostIp() {
		return sHostIp;
	}

	public void setsHostIp(String sHostIp) {
		this.sHostIp = sHostIp;
	}

	public String getsPort() {
		return sPort;
	}

	public void setsPort(String sPort) {
		this.sPort = sPort;
	}

	public Integer getnStatus() {
		return nStatus;
	}

	public void setnStatus(Integer nStatus) {
		this.nStatus = nStatus;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
}
