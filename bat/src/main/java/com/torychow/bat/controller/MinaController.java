/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-9-23 下午2:52:57 snail
 */
package com.torychow.bat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torychow.bat.bean.Host;
import com.torychow.bat.server.SingletonManager;
import com.torychow.bat.service.CrudService;
import com.torychow.bat.util.Constants;
import com.torychow.bat.util.JsonUtil;
import com.torychow.bat.util.ResponseMap;

@Controller
public class MinaController extends BaseController {
	@Resource
	private CrudService crudService;
	@Resource
	private HttpSession httpSession;
	
	@RequestMapping(value = "/getHost")
	public void getHost(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		GreenOfficeEmp emp= (GreenOfficeEmp) httpSession.getAttribute(Constants.GREEN_OFFICE_EMP);
//		List<EmpHostDbRelation> queryEmpHostDbRelation = empHostDbRelationService.queryEmpHostDbRelation(emp);
		ResponseMap map = new ResponseMap();
		Map<String, IoSession> map2 = SingletonManager.getInstance().getMap();
		Set<String> keySet = map2.keySet();
		Iterator<String> iter = keySet.iterator();
		List<Host> hostList = new ArrayList<Host>();
		while (iter.hasNext()) {
			Object key = iter.next();
			IoSession ioSession = map2.get(key);
			if (ioSession.isClosing()) {
				map2.remove(key);
			} else {
				if (null != ioSession.getAttribute("host")) {
					Host attribute = (Host) ioSession.getAttribute("host");
					hostList.add(attribute);
					/*for(EmpHostDbRelation e:queryEmpHostDbRelation){
						if(e.getHostDbByEmpRelation().getsHostIp().equals(attribute.getIp())){
							hostList.add(attribute);
							queryEmpHostDbRelation.remove(e);
							break;
						}
					}*/
				}
			}
		}
		String[] include = null;
		String[] exclude = { "portForHost", "processForHost" };
		map.put("hostList", hostList);
		map.success();
		this.getWriter(response).write(map.toJson(include, exclude));
	}

	@RequestMapping(value = "/getHostByIp")
	public void getHostByIp(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = Constants.HOST_IP, required = true) String hostIp)
			throws Exception {
		ResponseMap map = new ResponseMap();
		Host host = (Host) SingletonManager.getInstance().getSession("1-" + hostIp)
				.getAttribute("host");
		map.put("host", host);
		map.success();
		this.getWriter(response).write(map.toJson());
	}

	/**
	 * 执行操作
	 * 
	 * @author zhoutong
	 * @date 2014-9-28 下午12:10:49
	 */
	@RequestMapping(value = "/excuteCommander")
	public void excuteCommander(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = Constants.HOST_IP, required = true) String hostIp,
			@RequestParam(value = "commander", required = true) String commander)
			throws Exception {
		WriteFuture write = SingletonManager.getInstance()
				.getSession("2-" + hostIp).write(commander);
		System.out.println("this is the writefuture:" + write.toString());
		this.success(response);
	}

	@RequestMapping(value = "/terminal")
	public void terminal(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = Constants.HOST_IP, required = true) String hostIp,
			@RequestParam(value = "commander", required = true) String commander)
			throws Exception {
		ResponseMap map = new ResponseMap();
		Map<String, Object> cmdMap=new HashMap<String, Object>();
		String uuid=UUID.randomUUID().toString();
		cmdMap.put("type", "terminal");
		cmdMap.put("uuid", uuid);
		cmdMap.put("command",commander);
		IoSession ioSession = SingletonManager.getInstance()
				.getSession("2-" + hostIp);
		WriteFuture future = ioSession.write(JsonUtil.getJsonString4JavaPOJO(cmdMap));
		future.awaitUninterruptibly(10,TimeUnit.SECONDS);
		if (future.isWritten()) {
			long currentTimeMillis = System.currentTimeMillis();
			while(true){
				if(ioSession.containsAttribute(uuid)){
					Object message = ioSession.getAttribute(uuid);
					ioSession.removeAttribute(uuid);
					map.put("message", message);
					map.success();
					break;
				}
				if(System.currentTimeMillis()-currentTimeMillis>5000){
					map.setCodeInfo("请求超时！");
					break;
				}
				Thread.sleep(10);
			}
		} else {
			map.setCodeInfo("发送数据失败！");
		}
		this.getWriter(response).write(map.toJson());
	}
}
