package com.torychow.bat.service.impl;

import org.springframework.stereotype.Service;

import com.torychow.bat.dao.impl.BaseDaoImpl;
import com.torychow.bat.service.BaseService;

/**
 * @author zhoutong
 * @ClassName BaseServiceImpl
 * @Date create at 2013-5-24 上午11:32:40
 * @Commpany 蜗牛公司
 */
@Service
public class BaseServiceImpl<T> extends BaseDaoImpl<T> implements
		BaseService<T> {
}
