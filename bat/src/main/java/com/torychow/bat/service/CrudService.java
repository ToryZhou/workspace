package com.torychow.bat.service;

import java.util.List;
import java.util.Map;

import com.torychow.bat.util.Pagenation;



public interface CrudService {
	public List<Object> saveOrUpdate(String sParameters, String entity)
			throws Exception;
	public void delete(Integer[] nIds, String entity)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;
	public Pagenation<?> query(String entity, String whereJson,
			String orderJson, Integer nPageSize, Integer nCurrentPage,String likeJson) throws InstantiationException, IllegalAccessException,ClassNotFoundException;
	public Object get(Class<?> clazz,Map<String,Object> whereMap);
	public List<?> getEntitiesByJpql(Class<?> clazz,Map<String,Object> whereMap);
}
