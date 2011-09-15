package com.xrl.chexian.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xrl.chexian.Settings;
import com.xrl.chexian.model.Group;
import com.xrl.chexian.model.Result;


/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-8-31 下午02:03:52
 */
public class JsonToObjectUtils {

	private static JsonToObjectUtils instance= null;
	private static boolean DEBUG = Settings.DEBUG;
	private static String TAG = "JsonToObjectUtils";
	public static JsonToObjectUtils getInstance(){
		if(instance == null){
			instance = new JsonToObjectUtils();
		}
		return instance;
	}
	
	public  <T extends Result> Group<T> convertResult(JSONArray  jsonArray,Class<T> clazz) throws JSONException{
		Group<T> resultList = new Group<T>();
		try {
			for(int i =0;i<jsonArray.length();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				resultList.add(convertResult(jsonObject, clazz));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	
	public  <T extends Result> T convertResult(JSONObject  jsonObject,Class<T> clazz) throws JSONException{
		T returnResult = null;
		try {
			Result result = (Result)clazz.newInstance();
//			returnResult = result.initFormJson(jsonObject);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnResult;
	}
	
}
