/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-10-23 下午2:21:39 snail
 */
package com.torychow.bat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torychow.bat.bean.Visit;
import com.torychow.bat.service.BaseService;


@Controller
public class VisitController extends BaseController {
	
	@Resource
	private BaseService<?> baseService;
	
	@RequestMapping(value = "saveVisit")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Visit visit=new Visit();
		visit.setsVisitAddr(request.getRemoteAddr());
		visit.setsVisitHost(request.getRemoteHost());
		baseService.saveOrUpdateAndReturnO(visit);
		this.success(response);
	}
}
