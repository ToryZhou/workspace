package com.torychow.bat.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author Leo
 * @Description 封装返回给前台的Map和方法
 * @ClassName ResponseMap
 * @Date create at 2013-9-2 下午01:42:47
 * @Commpany 游戏蜗牛
 */
public class ResponseMap {

	private Map<String, Object> map;
	private static final String CODE = "code";
	private static final String CODEINFO = "codeInfo";

	/**
	 * 初始化map
	 */
	public ResponseMap() {
		this.map = new HashMap<String, Object>();
		map.put(CODE, CodeConstants.DEFAULT_ERROR_CODE);
		map.put(CODEINFO, CodeConstants.DEFAULT_ERROR_CODEINFO);
	}

	public ResponseMap(Map<String, Object> map) {
		this.map = map;
	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public void putAll(Map<String, Object> m) {
		map.putAll(m);
	}

	/**
	 * 操作成功
	 */
	public void success() {
		map.put(CODE, CodeConstants.DEFAULT_SUCCSESS_CODE);
		map.put(CODEINFO, CodeConstants.DEFAULT_SUCCSESS_CODEINFO);
	}

	/**
	 * 操作失败
	 */
	public void error() {
		map.put(CODE, CodeConstants.DEFAULT_ERROR_CODE);
		map.put(CODEINFO, CodeConstants.DEFAULT_ERROR_CODEINFO);
	}

	/**
	 * map对象转化为json字符串
	 */
	public String toJson(String... strs) {
		return strs.length > 0 ? JSONObject.fromObject(map,
				CommonUtils.getConfigByStr(new JsonConfig(), strs)).toString()
				: JSONObject.fromObject(map).toString();
	}
	
	public String toJsonExclude(String... strs) {
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(strs);
		return JSONObject.fromObject(map,jsonConfig).toString();
	}
	
	public String toJson(String[] include,String[] exclude) {
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 死循环监听
		if(ArrayUtils.isNotEmpty(exclude)){
			jsonConfig.setExcludes(exclude);
		}
		return ArrayUtils.isNotEmpty(include) ? JSONObject.fromObject(map,
				CommonUtils.getConfigByStr(jsonConfig, include)).toString()
				: JSONObject.fromObject(map,jsonConfig).toString();
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getCode() {
		return (Integer) map.get(CODE);
	}

	public void setCode(int code) {
		map.put(CODE, code);
	}

	public String getCodeInfo() {
		return (String) map.get(CODEINFO);
	}

	public void setCodeInfo(String codeInfo) {
		map.put(CODEINFO, codeInfo);
	}

}
