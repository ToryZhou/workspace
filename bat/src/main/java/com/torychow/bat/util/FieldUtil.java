/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2014-8-11 下午1:35:27 snail
 */
package com.torychow.bat.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FieldUtil {
	/**
	 * 根据属性名获取属性值
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * */
    public static Object getFieldValueByName(String fieldName, Object o) throws Exception {
    	String getter = "get" + fieldName;
    	Method method=null;
    	try{
    		method = o.getClass().getMethod(getter, new Class[] {});  
    	}catch(NoSuchMethodException e){
    		String firstLetter = null;  
    		firstLetter = fieldName.substring(0, 1);
    		char c=firstLetter.charAt(0);
    		if(Character.isLowerCase(c)){
    			firstLetter=firstLetter.toUpperCase();
    		}
    		else{
    			firstLetter=firstLetter.toLowerCase();
    		}
    		getter = "get" + firstLetter + fieldName.substring(1);
    		if("serialVersionUID".equals(fieldName)){
    			getter="getSerialversionuid";
    		}
    		method = o.getClass().getMethod(getter, new Class[] {});
    	}
    	Object value = method.invoke(o, new Object[] {});
    	return value;
    	
    	/*try {    
            String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1);    
            Method method = o.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(o, new Object[] {});    
            return value;    
        } catch (Exception e) {    
            log.error(e.getMessage(),e);    
            return null;    
        }*/  
    } 
    
    public static void setFieldValueByName(String fieldName, Object object,Object value) throws Exception{
    	setFieldValueByName(getFiledByName(object, fieldName), object, value);
    }
    
    public static void setFieldValueByName(Field field, Object object,Object value) throws Exception {
    	String fieldName=field.getName();
    	if("serialVersionUID".equals(fieldName)){
    		return;
		}
    	String setter = "set" + fieldName;
    	Method method=null;
    	try{
    		method = object.getClass().getDeclaredMethod(setter, field.getType());  
    	}catch(NoSuchMethodException e){
    		String firstLetter = null;  
    		firstLetter = fieldName.substring(0, 1);
    		char c=firstLetter.charAt(0);
    		if(Character.isLowerCase(c)){
    			firstLetter=firstLetter.toUpperCase();
    		}
    		else{
    			firstLetter=firstLetter.toLowerCase();
    		}
    		setter = "set" + firstLetter + fieldName.substring(1);
    		method = object.getClass().getMethod(setter, field.getType());
    	}
    	 method.invoke(object,value);
    } 
   
    
    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * @throws Exception 
     * */
    public static List<Map<String,Object>> getFiledsInfo(Object o) throws Exception{
    	Field[] fields=o.getClass().getDeclaredFields();
       	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
       	Map<String,Object> infoMap=null;
    	for(int i=0;i<fields.length;i++){
    		infoMap = new HashMap<String,Object>();
    		infoMap.put("type", fields[i].getType().toString());
    		infoMap.put("name", fields[i].getName());
    		infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
    		list.add(infoMap);
    	}
    	return list;
    }
    
    /**
     * 获取对象的所有属性值，返回一个对象数组
     * @throws Exception 
     * */
    public static Object[] getFiledValues(Object o) throws Exception{
    	String[] fieldNames=getFiledName(o);
    	Object[] value=new Object[fieldNames.length];
    	for(int i=0;i<fieldNames.length;i++){
    		value[i]=getFieldValueByName(fieldNames[i], o);
    	}
    	return value;
    }	
    
    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
    	Field[] fields=o.getClass().getDeclaredFields();
       	String[] fieldNames=new String[fields.length];
    	for(int i=0;i<fields.length;i++){
    		fieldNames[i]=fields[i].getName();
    	}
    	return fieldNames;
    }
    
    public static Field getFiledByName(Object o,String name){
    	Field[] fields=o.getClass().getDeclaredFields();
    	for(int i=0;i<fields.length;i++){
    		if(name.equals(fields[i].getName())){
    			return fields[i];
    		}
    	}
		return null;
    }
    
    public static String[] getFiledMethod(Object o){
    	Method[] methods = o.getClass().getMethods();
       	String[] methodNames=new String[methods.length];
    	for(int i=0;i<methods.length;i++){
    		methodNames[i]=methods[i].getName();
    	}
    	return methodNames;
    }
    
    /**
     * @description  把object中的非空值赋给objectOld
     * @author zhoutong
     * @date 2014-9-10 上午11:17:34
     */
	public static Object getInstance(Object object, Object objectOld)
			throws Exception {
		Field[] declaredFields = objectOld.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			String  fieldName= field.getName();
			if (fieldName.equalsIgnoreCase("serialVersionUID")
					||fieldName.equalsIgnoreCase("handler")
					||fieldName.equalsIgnoreCase("_filter_signature")
					||fieldName.equalsIgnoreCase("_methods_")
					)
				continue;
			Object fieldValueByName = FieldUtil.getFieldValueByName(fieldName, object);
			if (null != fieldValueByName) {
				setFieldValueByName(field, objectOld, fieldValueByName);
//				field.setAccessible(true);//加了这句反射也存在private问题
//				field.set(objectOld, fieldValueByName);
			}
		}
		return objectOld;
	}
	/**
	 * 
	 * @description 1、把object中的非空值赋给objectOld，clazz为对象的类别，exclude为不进行赋值的
	 * @description 2、把object深度克隆到objectOld
	 * @author zhoutong
	 * @date 2014-9-22 下午4:39:34
	 */
	public static Object getInstance(Object object, Object objectOld,Class<?> clazz,List<String> exclude)
			throws Exception {
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			Boolean flag=false;
			String  fieldName= field.getName();
			for(String s:exclude){
				if (fieldName.equalsIgnoreCase(s)){
					flag=true;
					break;
				}
			}
			if(flag){
				continue;
			}
			Object fieldValueByName = FieldUtil.getFieldValueByName(fieldName, object);
			if (null != fieldValueByName) {
				setFieldValueByName(field, objectOld, fieldValueByName);
			}
		}
		return objectOld;
	}
}
