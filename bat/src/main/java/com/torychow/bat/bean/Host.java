/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午1:27:12 snail
 */
package com.torychow.bat.bean;

import java.util.List;

public class Host {
	private String name;
	private String hostMemo;
	private String ip;
	private Integer status;//0-正常，1-预警，2-异常，3-客户端已断开,4-新建(还未进行检测)
	private String osName;
	private Float cpuPercent;//使用率
	private Float cpuUser;
	private Float cpuSystem;
	private Float cpuIdle;
	private Float memoryPercent;
	private Float memoryTotal;
	private Float memoryUsed;
	private Float diskPercent;
	private Float diskTotal;//该磁盘分区的大小
	private Float diskUsed;
	private List<Port> portForHost;
	private List<Process> processForHost;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Port> getPortForHost() {
		return portForHost;
	}
	public void setPortForHost(List<Port> portForHost) {
		this.portForHost = portForHost;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public List<Process> getProcessForHost() {
		return processForHost;
	}
	public void setProcessForHost(List<Process> processForHost) {
		this.processForHost = processForHost;
	}
	public Float getCpuPercent() {
		return cpuPercent;
	}
	public void setCpuPercent(Float cpuPercent) {
		this.cpuPercent = cpuPercent;
	}
	public Float getMemoryPercent() {
		return memoryPercent;
	}
	public void setMemoryPercent(Float memoryPercent) {
		this.memoryPercent = memoryPercent;
	}
	public Float getDiskPercent() {
		return diskPercent;
	}
	public void setDiskPercent(Float diskPercent) {
		this.diskPercent = diskPercent;
	}
	public Float getCpuUser() {
		return cpuUser;
	}
	public void setCpuUser(Float cpuUser) {
		this.cpuUser = cpuUser;
	}
	public Float getCpuSystem() {
		return cpuSystem;
	}
	public void setCpuSystem(Float cpuSystem) {
		this.cpuSystem = cpuSystem;
	}
	public Float getCpuIdle() {
		return cpuIdle;
	}
	public void setCpuIdle(Float cpuIdle) {
		this.cpuIdle = cpuIdle;
	}
	public Float getMemoryTotal() {
		return memoryTotal;
	}
	public void setMemoryTotal(Float memoryTotal) {
		this.memoryTotal = memoryTotal;
	}
	public Float getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(Float memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	public Float getDiskTotal() {
		return diskTotal;
	}
	public void setDiskTotal(Float diskTotal) {
		this.diskTotal = diskTotal;
	}
	public Float getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(Float diskUsed) {
		this.diskUsed = diskUsed;
	}
	public String getHostMemo() {
		return hostMemo;
	}
	public void setHostMemo(String hostMemo) {
		this.hostMemo = hostMemo;
	}
	
}
