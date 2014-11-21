/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午1:27:12 snail
 */
package com.torychow.bat.bean;

public class Process {
	private String name;
	private Integer pid;
	private Integer rss;
	private Integer vms;
	private Integer cpu;
	private String cwd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getRss() {
		return rss;
	}
	public void setRss(Integer rss) {
		this.rss = rss;
	}
	public Integer getVms() {
		return vms;
	}
	public void setVms(Integer vms) {
		this.vms = vms;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public String getCwd() {
		return cwd;
	}
	public void setCwd(String cwd) {
		this.cwd = cwd;
	}
	
	
}
