package com.torychow.bat.util;

import org.json.JSONObject;


/**
 * JSON解析类
 */
public class JsonUtilOrg {

	public static Object json4ObjectOrg(Object message,Object object) throws Exception{
    	String s=(String)message;
    	JSONObject jo=new JSONObject(s);
    	String[] names = JSONObject.getNames(jo);
    	for(String s2:names){
    		FieldUtil.setFieldValueByName(s2, object, jo.get(s2));
    	}
    	return object;
    }
	
	/*public static List<? extends Object> getList4Json(String jsonString){
		List<?> list = new ArrayList<Object>();
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			list=(List<?>) jsonArray;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}*/
}

