package com.torychow.bat.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.torychow.bat.dao.BaseDao;
import com.torychow.bat.util.CommonUtils;
import com.torychow.bat.util.Constants;
import com.torychow.bat.util.JsonUtil;
import com.torychow.bat.util.Pagenation;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	@PersistenceContext(unitName = "batPersist")
	protected EntityManager entityManager;

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void remove(T entity) {
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}

	public void _remove(T entity) {
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void remove(T entity, Object id) {
		this.remove(this.get(entity, id));
	}

	public void _remove(T entity, Object id) {
		this._remove(this.get(entity, id));
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void remove(T entity, Object[] ids) {
		for (Object id : ids) {
			this._remove(this.get(entity, id));
		}
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void remove(Class<?> clazz, Object[] ids) {
		for (Object id : ids) {
			this._remove(this.get(clazz, id));
		}
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void excuteJpql(String jpql, Object[] params) {
		entityManager.flush();
		Query query = entityManager.createQuery(jpql);
		if (null != params && params.length > 0) {
			for (int pos = 0; pos < params.length; pos++) {
				query.setParameter(pos + 1, params[pos]);
			}
		}
		query.executeUpdate();
	}

	public void _excuteJpql(String jpql, Object[] params) {
		entityManager.flush();
		Query query = entityManager.createQuery(jpql);
		if (null != params && params.length > 0) {
			for (int pos = 0; pos < params.length; pos++) {
				query.setParameter(pos + 1, params[pos]);
			}
		}
		query.executeUpdate();
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public T saveOrUpdateAndReturn(T domain) {
		return entityManager.merge(domain);
	}

	public T _saveOrUpdateAndReturn(T domain) {
		return entityManager.merge(domain);
	}

	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public Object saveOrUpdateAndReturnO(Object object){
		return entityManager.merge(object);
	}
	
	public Object _saveOrUpdateAndReturnO(Object object){
		return entityManager.merge(object);
	}
	
	@Transactional(value = Constants.BAT_TRANSACTION_MANAGER)
	public void persist(Object object) {
		entityManager.persist(object);
	}
	
	public void _persist(Object object) {
		entityManager.persist(object);
	}
	
	/*public void _persist(Object[] object) {
		entityManager.persist(object);
	}*/
	

	public List<?> getEntitiesByJpql(String jpql, Object[] params) {
		Query query = setQuery(jpql, params);
		List<?> result = query.getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getEntitiesByJpqlT(String jpql, Object[] params) {
		Query query = setQuery(jpql, params);
		List<T> result = query.getResultList();
		return result;
	}
	
	public T get(T entity, Object id) {
		return (T) entityManager.find(entity.getClass(), id);
	}

	public T get(Class<?> clazz, Object id) {
		return (T) entityManager.find(clazz, id);
	}
	
	public Pagenation<T> getPagenationEntity(Integer nPageSize,
			Integer nCurrentPage, String jpql, String jpqlCount, Object[] params) {
		if (null == nPageSize)
			nPageSize = 99999;
		if (null == nCurrentPage)
			nCurrentPage = 1;
		Pagenation<T> page = new Pagenation<T>();
		page.setPageSize(nPageSize);
		int totalRecord = 0;
		if (null != jpqlCount) {
			totalRecord = countRecord(jpqlCount, params);
		} else {
			totalRecord = countRecordSize(jpql, params);
		}
		int pageCount = (int) Math.ceil((totalRecord * 1.0) / nPageSize);
		nCurrentPage = CommonUtils.getCurrentPage(totalRecord, nCurrentPage,
				nPageSize) - 1;
		page.setCurrentPage(nCurrentPage + 1);
		page.setTotalRecord(totalRecord);
		page.setPageCount(pageCount);
		Query query = setQuery(jpql, params, nPageSize, nCurrentPage);
		List<T> result = query.getResultList();
		page.setEntityList(result);
		return page;
	}

	private int countRecord(String jpql, Object[] params) {
		long count = (Long) getSingleResultByJpql(jpql, params);
		return (int) count;
	}

	private int countRecordSize(String jpql, Object[] params) {
		int count = 0;
		Query query = setQuery(jpql, params);
		count = query.getResultList().size();
		return count;
	}

	public Object getSingleResultByJpql(String jpql, Object[] params) {
		Query query = setQuery(jpql, params);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * whereJson 条件键值对 orderJson 排序键值对 joinList 懒加载对象变量名
	 */
	public Pagenation<T> query(String whereJson, String orderJson,
			Integer nPageSize, Integer nCurrentPage, T entity,
			List<String> joinList) {
		Pagenation<T> pagenation = new Pagenation<T>();
		StringBuffer jpqlWhere = new StringBuffer();
		StringBuffer jpqlFrom = new StringBuffer();
		StringBuffer jpqlFromCount = new StringBuffer();
		jpqlFrom.append("select o from " + entity.getClass().getName() + " o");
		jpqlFromCount.append("select count(o) from "
				+ entity.getClass().getName() + " o");
		List<Object> params = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(joinList)) {
			for (String join : joinList) {
				jpqlFrom.append(" left join fetch o." + join);
			}
		}
		if (null != whereJson) {
			jpqlWhere.append(" where");
			Map<String, Object> map = JsonUtil.getMap4Json(whereJson);
			Integer size = map.size();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + "=?");
				params.add(map.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(" and");
			}
		}
		if (null != orderJson) {
			jpqlWhere.append(" order by");
			Map<String, Object> map = JsonUtil.getMap4Json(orderJson);
			Integer size = map.size();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + " " + map.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(",");
			}
		}
		pagenation = this.getPagenationEntity(nPageSize, nCurrentPage, jpqlFrom
				.append(jpqlWhere).toString(), jpqlFromCount.append(jpqlWhere)
				.toString(), params.toArray());
		return pagenation;
	}
	
	public Pagenation<T> query(String whereJson, String orderJson,
			Integer nPageSize, Integer nCurrentPage, Class<?> clazz,
			List<String> joinList,Map<Object,Object> joinMap,
			Map<String, Object> whereMap,String likeJson,String whereJpql) {
		Pagenation<T> pagenation = new Pagenation<T>();
		StringBuffer jpqlWhere = new StringBuffer();
		StringBuffer jpqlFrom = new StringBuffer();
		StringBuffer jpqlFromCount = new StringBuffer();
		jpqlFrom.append("select o from " + clazz.getName() + " o");
		jpqlFromCount.append("select count(o) from "
				+ clazz.getName() + " o");
		List<Object> params = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(joinList)) {
			for (String join : joinList) {
				jpqlFrom.append(" left join fetch o." + join);
			}
		}
		if (null != joinMap) {
			Set<Object> keySet = joinMap.keySet();
			Iterator<Object> iter = keySet.iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				jpqlFrom.append(" left join fetch " + key.toString() + "."
						+ joinMap.get(key).toString());
			}
		}
		if(null!=whereMap){
			Map<String, Object> map4Json=whereMap;
			if(null!=whereJson){map4Json = JsonUtil.getMap4Json(whereJson);}
			map4Json.putAll(whereMap);
			whereJson = JsonUtil.getJsonString4JavaPOJO(map4Json);
		}
		if (null != whereJson) {
			jpqlWhere.append(" where");
			Map<String, Object> map = JsonUtil.getMap4Json(whereJson);
			Integer size = map.size();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + "=?");
				params.add(map.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(" and");
			}
		}
		if(null!=likeJson){
			if(null == whereJson){
				jpqlWhere.append(" where");
			}else{
				jpqlWhere.append(" and");
			}
			Map<String, Object> map = JsonUtil.getMap4Json(likeJson);
			Integer size = map.size();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + " like ?");
				params.add("%"+map.get(key)+"%");
				size--;
				if (size > 0)
					jpqlWhere.append(" and");
			}
		}
		if(null!=whereJpql){
			if(null==whereJson&&null==likeJson){
				jpqlWhere.append(" where");
			}else{
				jpqlWhere.append(" and ");
			}
			jpqlWhere.append(whereJpql);
		}
		if (null != orderJson) {
			jpqlWhere.append(" order by");
			Map<String, Object> map = JsonUtil.getMap4Json(orderJson);
			Integer size = map.size();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + " " + map.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(",");
			}
		}
		pagenation = this.getPagenationEntity(nPageSize, nCurrentPage, jpqlFrom
				.append(jpqlWhere).toString(), jpqlFromCount.append(jpqlWhere)
				.toString(), params.toArray());
		return pagenation;
	}

	private Query setQuery(String jpql, Object[] params) {
		Query query = entityManager.createQuery(jpql);
		if (null != params && params.length > 0) {
			for (int pos = 0; pos < params.length; pos++) {
				query.setParameter(pos + 1, params[pos]);
			}
		}
		return query;
	}

	private Query setQuery(String jpql, Object[] params, Integer nPageSize,
			Integer nCurrentPage) {
		Query query = entityManager.createQuery(jpql);
		if (null != params && params.length > 0) {
			for (int pos = 0; pos < params.length; pos++) {
				query.setParameter(pos + 1, params[pos]);
			}
		}
		if (null != nPageSize && null != nCurrentPage) {
			query.setFirstResult(nPageSize * nCurrentPage);
			query.setMaxResults(nPageSize);
		}
		return query;
	}

}
