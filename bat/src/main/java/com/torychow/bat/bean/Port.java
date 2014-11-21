/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午1:27:12 snail
 */
package com.torychow.bat.bean;

public class Port {
	private String name;
	private String port;
	private String host;
	private String memo;
	private Boolean isOpen;
	private Boolean autoReconnect;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public Boolean getAutoReconnect() {
		return autoReconnect;
	}
	public void setAutoReconnect(Boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
}
