/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-24 上午11:01:45 snail
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
public class Website {
	private Integer nWebsiteId;
	private String sWebsiteName;
	private String sWebsiteUrl;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public Integer getnWebsiteId() {
		return nWebsiteId;
	}
	public void setnWebsiteId(Integer nWebsiteId) {
		this.nWebsiteId = nWebsiteId;
	}
	public String getsWebsiteName() {
		return sWebsiteName;
	}
	public void setsWebsiteName(String sWebsiteName) {
		this.sWebsiteName = sWebsiteName;
	}
	public String getsWebsiteUrl() {
		return sWebsiteUrl;
	}
	public void setsWebsiteUrl(String sWebsiteUrl) {
		this.sWebsiteUrl = sWebsiteUrl;
	}
}
