package com.xrl.chexian.utils.gson;

import java.lang.reflect.Type;
import java.text.DateFormat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * GSON转换帮助方法
 * 
 * @author sanvi
 * 
 */

public class GsonUtils {

	private static GsonUtils instance;

	/**
	 * 获取一个实例
	 * 
	 * @return
	 * @date 2010-3-11
	 */
	public static GsonUtils getInstance() {
		if (instance == null) {
			instance = new GsonUtils();
		}
		return instance;
	}

	/**
	 * 序列化方法
	 * 
	 * @param bean
	 * @param type
	 */
	public String bean2json(Object bean) {
		com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new UtilDateSerializer()).setDateFormat(DateFormat.LONG)
				.create();
		return gson.toJson(bean);
	}

	/**
	 * 反序列化方法
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public <T> T json2bean(String json, Type typeOfT) {
		com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer()).setDateFormat(DateFormat.LONG)
				.create();
		return (T) gson.fromJson(json, typeOfT);

	}

	public <T> T json2bean(String json, Class<T> clazz) {
		com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer()).setDateFormat(DateFormat.LONG)
				.create();
		return gson.fromJson(json, clazz);
	}

	public static String str2json(String str) {
		JsonObject jo = new JsonObject();
		jo.addProperty("data", str);
		return jo.toString();
	}

	// public static void main(String[] arg){
	// System.out.println(str2json("asdfsadfs,asdfasdf[sadfo]"));
	// System.exit(0);
	// }

}
