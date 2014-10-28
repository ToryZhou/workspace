/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-23 下午2:21:39 snail
 */
package com.torychow.bat.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torychow.bat.service.CrudService;
import com.torychow.bat.util.ResponseMap;


@Controller
public class MessageController extends BaseController {
	
	@Resource
	private CrudService crudService;
	
	@RequestMapping(value = "queryMessage")
	public void queryMessage(
			HttpServletResponse response,
			@RequestParam(value = "whereJson", required = false) String whereJson,
			@RequestParam(value = "orderJson", required = false) String orderJson,
			@RequestParam(value = "nPageSize", required = false) Integer nPageSize,
			@RequestParam(value = "nCurrentPage", required = false) Integer nCurrentPage,
			@RequestParam(value = "likeJson", required = false) String likeJson)
			throws Exception {
		ResponseMap map = new ResponseMap();
		HashMap<String, Object> whereMap=new HashMap<String, Object>();
		whereMap.put("isMessagePrivate", false);
		String[] include=null;
		String[] exclude=null;
		map.put("pagenation", crudService.query("Message", whereJson, orderJson, nPageSize, nCurrentPage,likeJson,whereMap));
		map.success();
		this.getWriter(response).write(map.toJson(include,exclude));
	}
	
}
