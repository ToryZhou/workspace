package com.torychow.bat.dao;

import java.util.List;
import java.util.Map;

import com.torychow.bat.util.Pagenation;

public interface BaseDao<T> {

	public void remove(T entity);

	public void _remove(T entity);

	public void remove(T entity, Object id);

	public void _remove(T entity, Object id);

	public void remove(T entity, Object[] ids);

	public void remove(Class<?> clazz, Object[] ids);

	public void excuteJpql(String jpql, Object[] params);

	public void _excuteJpql(String jpql, Object[] params);

	public T saveOrUpdateAndReturn(T domain);

	public T _saveOrUpdateAndReturn(T domain);

	public Object saveOrUpdateAndReturnO(Object object);
	
	public Object _saveOrUpdateAndReturnO(Object object);

	public void persist(Object object);
	public void _persist(Object object);
//	public void _persist(Object[] object);

	public Pagenation<T> getPagenationEntity(Integer nPageSize,
			Integer nCurrentPage, String jpql, String jpqlCount, Object[] params);

	public Pagenation<T> query(String whereJson, String orderJson,
			Integer nPageSize, Integer nCurrentPage, T entity,
			List<String> joinList);

	public Pagenation<T> query(String whereJson, String orderJson,
			Integer nPageSize, Integer nCurrentPage, Class<?> clazz,
			List<String> joinList, Map<Object, Object> joinMap,
			Map<String, Object> whereMap, String likeJson, String whereJpql);

	public List<?> getEntitiesByJpql(String jpql, Object[] params);
	public List<T> getEntitiesByJpqlT(String jpql, Object[] params);

	public T get(T entity, Object id);

	public Object get(Class<?> clazz, Object id);

	public Object getSingleResultByJpql(String jpql, Object[] params);
}
