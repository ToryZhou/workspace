/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午4:48:54 snail
 */
package com.torychow.bat.server;

public class MessageBean {
	private Integer id;
	private String socketThreadNumber;
	private String host;
	private String port;
	private String content;
	private String type;
	private String osName;
	private String hostName;
	private String uuid;
	private String hostMemo;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSocketThreadNumber() {
		return socketThreadNumber;
	}
	public void setSocketThreadNumber(String socketThreadNumber) {
		this.socketThreadNumber = socketThreadNumber;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getHostMemo() {
		return hostMemo;
	}
	public void setHostMemo(String hostMemo) {
		this.hostMemo = hostMemo;
	}
}
