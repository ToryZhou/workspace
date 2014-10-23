package com.torychow.bat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.torychow.bat.exception.CodeInfoException;
import com.torychow.bat.exception.PermissionDeniedException;
import com.torychow.bat.util.Constants;
import com.torychow.bat.util.ResponseMap;


@Controller
public abstract class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public PrintWriter getWriter(HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		return response.getWriter();
	}

	public void write(HttpServletResponse response, ResponseMap map,
			String... config) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(map.toJson(config));
	}

	public void success(HttpServletResponse response) throws IOException {
		ResponseMap map = new ResponseMap();
		map.success();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(map.toJson());
	}

	public void success(HttpServletResponse response, String key, Object obj,
			String... config) throws IOException {
		ResponseMap map = new ResponseMap();
		map.put(key, obj);
		map.success();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(map.toJson(config));
	}

	@ExceptionHandler(value = Exception.class)
	public void handleException(Exception e, HttpServletResponse response)
			throws Exception {
		ResponseMap map = new ResponseMap();
		map.error();

		String msg = e.getMessage();
		int code=-1;
		if (e instanceof CodeInfoException) {
			code=((CodeInfoException) e).getCode();
			msg = ((CodeInfoException) e).getCodeInfo();
			logger.debug(msg);
		} else if (e instanceof NumberFormatException) {
			msg = "数字参数错误";
			logger.error("", e);
		} else if (e instanceof PermissionDeniedException) {
			map.setCode(Constants.CODE_PERMISSION_DENIED);
			msg = "没有权限:"+e.getMessage();
		} else {
			msg =e.getMessage();
			logger.error("", e);
		}
		/*logger.error(e.getClass().getName() + "$" + e.getMessage()!=null?e.getMessage():"错误信息为空！");*/
		map.setCodeInfo(msg);
		map.setCode(code);
		System.out.println(e.getClass().getName() + "$" + e.getMessage());
		this.write(response, map);
	}
	
}
