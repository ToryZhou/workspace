/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-19 上午10:22:12 snail
 */
package com.torychow.bat.server;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.torychow.bat.bean.Host;
import com.torychow.bat.bean.Log;
import com.torychow.bat.bean.Port;
import com.torychow.bat.service.BaseService;
import com.torychow.bat.util.FieldUtil;
import com.torychow.bat.util.JsonUtil;
import com.torychow.bat.bean.Process;


 
public class MinaServerHanlder extends IoHandlerAdapter {
 
	@Resource
	private BaseService<?> baseService;
    private int count = 0;
    
    // 当一个新客户端连接后触发此方法.
    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("新客户端连接");
    }
 
    // 当一个客端端连结进入时
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        count++;
        System.out.println("第 " + count + " 个 client 登陆！address： : "
                + session.getRemoteAddress());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception{
    	System.out.println(message);
    	MessageBean  messageBean=(MessageBean) JsonUtil.getObject4JsonObject(message, MessageBean.class);
    	if("socket".equals(messageBean.getType())){
    		session.setAttribute("socketThreadNumber", messageBean.getSocketThreadNumber());
    		Host host=new Host();
    		host.setStatus(4);
    		host.setIp(messageBean.getHost());
    		if(messageBean.getSocketThreadNumber().equals("1")){
    			session.setAttribute("host", host);
    			host.setOsName(messageBean.getOsName());
    			host.setName(messageBean.getHostName());
    			host.setHostMemo(messageBean.getHostMemo());
    		}
    		SingletonManager.getInstance().setSession(messageBean.getSocketThreadNumber()+"-"+messageBean.getHost(), session);
    	}else if("config".equals(messageBean.getType())){
    		Host host=(Host) session.getAttribute("host");
			List<Port> list4Json = (List<Port>) JsonUtil.getList4Json(messageBean.getContent(), Port.class);
    		Boolean flag=false;
    		for(Port port:list4Json){
    			if(!port.getIsOpen()){
    				host.setStatus(2);
    				flag=true;
    				Log log=new Log();
        			log.setnStatus(2);
        			log.setsHostIp(host.getIp());
        			log.setsPort(port.getPort());
        			baseService.saveOrUpdateAndReturnO(log);
    			}
    		}
    		if(!flag){
    			host.setStatus(0);
    		}
    		host.setPortForHost(list4Json);
    	}else if("process".equals(messageBean.getType())){
    		Host host=(Host) session.getAttribute("host");
			List<com.torychow.bat.bean.Process> list4Json = (List<com.torychow.bat.bean.Process>) JsonUtil.getList4Json(messageBean.getContent(), Process.class);
    		host.setProcessForHost(list4Json);
    	}else if("host".equals(messageBean.getType())){
    		Host host=(Host) session.getAttribute("host");
    		Host hostNew = (Host) JsonUtil.getObject4JsonString(messageBean.getContent(), Host.class);
    		FieldUtil.getInstance(hostNew, host);
    	}else if(StringUtils.isNotBlank(messageBean.getUuid())&&"terminal".equals(messageBean.getType())){
    		session.setAttribute(messageBean.getUuid(),messageBean.getContent());
    	}
    	session.write("return message!");
    }
    
    // 当信息已经传送给客户端后触发此方法.
    @Override
    public void messageSent(IoSession session, Object message) {
        System.out.println("信息已经传送给客户端");
 
    }
 
    // 当一个客户端关闭时
    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("one Clinet Disconnect !");
    }
 
    // 当连接空闲时触发此方法.
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("连接空闲");
    }
 
    // 当接口中其他方法抛出异常未被捕获时触发此方法
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
    	System.out.println(cause.getMessage());
        System.out.println("其他方法抛出异常");
    }

}