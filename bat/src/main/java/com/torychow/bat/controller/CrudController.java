package com.torychow.bat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torychow.bat.service.CrudService;
import com.torychow.bat.util.ResponseMap;

/**
 * 增删改查通用Controller
 */
@Controller
public class CrudController extends BaseController {
	@Resource
	private CrudService crudService;
	@Resource
	private HttpSession httpSession;

	@RequestMapping(value = "/{entity}/saveOrUpdate")
	public void saveOrUpdate(
			HttpServletResponse response,
			@PathVariable("entity") String entity,
			@RequestParam(value = "sParameters", required = true) String sParameters)
			throws Exception {
		ResponseMap map = new ResponseMap();
		List<Object> saveOrUpdate = crudService.saveOrUpdate(sParameters, entity);
		map.put("objectList", saveOrUpdate);
		map.success();
		this.getWriter(response).write(map.toJson());
	}

	@RequestMapping(value = "/{entity}/delete")
	public void delete(HttpServletResponse response,
			@PathVariable("entity") String entity,
			@RequestParam(value = "nIds", required = true) Integer[] nIds)
			throws Exception {
		crudService.delete(nIds, entity);
		this.success(response);
	}

	@RequestMapping(value = "/{entity}/query")
	public void query(
			HttpServletResponse response,
			@PathVariable("entity") String entity,
			@RequestParam(value = "whereJson", required = false) String whereJson,
			@RequestParam(value = "orderJson", required = false) String orderJson,
			@RequestParam(value = "nPageSize", required = false) Integer nPageSize,
			@RequestParam(value = "nCurrentPage", required = false) Integer nCurrentPage,
			@RequestParam(value = "likeJson", required = false) String likeJson)
			throws Exception {
		ResponseMap map = new ResponseMap();
		String[] include=null;
		String[] exclude=null;
		map.put("pagenation", crudService.query(entity, whereJson, orderJson, nPageSize, nCurrentPage,likeJson));
		map.success();
		this.getWriter(response).write(map.toJson(include,exclude));
	}
}
