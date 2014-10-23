package com.torychow.bat.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.torychow.bat.service.BaseService;
import com.torychow.bat.service.CrudService;
import com.torychow.bat.util.Constants;
import com.torychow.bat.util.FieldUtil;
import com.torychow.bat.util.JsonUtil;
import com.torychow.bat.util.Pagenation;

@Service
public class CrudServiceImpl implements CrudService {
	@Resource
	private BaseService<?> baseService;

	@Override
	@Transactional(rollbackFor = Exception.class, value = Constants.BAT_TRANSACTION_MANAGER)
	public List<Object> saveOrUpdate(String sParameters, String entity)
			throws Exception {
		Class<?> clazz = Class
				.forName("com.snail.mina.bean." + entity);
		List<? extends Object> list4Json = JsonUtil.getList4Json(sParameters,
				clazz);
		List<Object> objectList = new ArrayList<Object>();
		for (Object objectNew : list4Json) {
			Object fieldValueByName = FieldUtil.getFieldValueByName("n"
					+ entity + "Id", objectNew);
			if (null != fieldValueByName) {
				Object object = baseService.get(clazz, fieldValueByName);
				FieldUtil.getInstance(objectNew, object);
			} else {
				baseService._persist(objectNew);
			}
			objectList.add(objectNew);
		}
		return objectList;
	}

	public void delete(Integer[] nIds, String entity)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> clazz = Class
				.forName("com.snail.mina.bean." + entity);
		baseService.remove(clazz, nIds);
	}

	public Pagenation<?> query(String entity, String whereJson,
			String orderJson, Integer nPageSize, Integer nCurrentPage,
			String likeJson) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<?> clazz = Class.forName("com.snail.mina.bean." + entity);
		List<String> joinList = new ArrayList<String>();
		HashMap<String, Object> whereMap = null;
		LinkedHashMap<Object, Object> joinMap = null;// LinkedHashMap用Iterator遍历时记录了插入顺序
		Pagenation<?> pagenation = null;
		String whereJpql = null;
		pagenation = baseService.query(whereJson, orderJson, nPageSize,
				nCurrentPage, clazz, joinList, joinMap, whereMap, likeJson,
				whereJpql);
		return pagenation;
	}
	
	public Object get(Class<?> clazz,Map<String,Object> whereMap){
		List<Object> params=new ArrayList<Object>();
		StringBuffer jpqlWhere=new StringBuffer();
		if(null!=whereMap){
			jpqlWhere.append(" where");
			Set<String> keySet = whereMap.keySet();	
			Integer size = whereMap.size();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + "=?");
				params.add(whereMap.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(" and");
			}
		}
		String jpql="select o from "+clazz.getSimpleName()+" o" +jpqlWhere;
		return baseService.getSingleResultByJpql(jpql, params.toArray());
	}
	
	public List<?> getEntitiesByJpql(Class<?> clazz,Map<String,Object> whereMap){
		List<Object> params=new ArrayList<Object>();
		StringBuffer jpqlWhere=new StringBuffer();
		if(null!=whereMap){
			jpqlWhere.append(" where");
			Set<String> keySet = whereMap.keySet();	
			Integer size = whereMap.size();
			for (String key : keySet) {
				jpqlWhere.append(" o." + key + "=?");
				params.add(whereMap.get(key));
				size--;
				if (size > 0)
					jpqlWhere.append(" and");
			}
		}
		String jpql="select o from "+clazz.getSimpleName()+" o" +jpqlWhere;
		return baseService.getEntitiesByJpql(jpql, params.toArray());
	}
}
