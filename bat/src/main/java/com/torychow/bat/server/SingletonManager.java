/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-24 下午2:13:40 snail
 */
package com.torychow.bat.server;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

/**
 * socket and session manager class
 */
public class SingletonManager {
	
	private static SingletonManager instance = null;
	private Map<String,  IoSession> map=new LinkedHashMap<String,IoSession>();
	private IoSession session;
	
	private SingletonManager() {
	}
	
	public static SingletonManager getInstance() {
		if (instance == null) {
			synchronized (SingletonManager.class) {
				if (instance == null) {
					instance = new SingletonManager();
				}
			}
		}
		return instance;
	}

	public IoSession getSession(String key) {
		return map.get(key);
	}
	
	public void setSession(String key, IoSession session) {
		if (session != null) {
			map.put(key, session);
		}
	}

	public Map<String, IoSession> getMap() {
		return map;
	}

	public void setMap(Map<String, IoSession> map) {
		this.map = map;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
}