package com.xrl.chexian.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author Sanvi E-mail:sanvibyfish@gmail.com
 * @version 创建时间：2010-8-31 下午01:18:16
 */
public interface Result {
	public <T extends Result>  T initFormJson(JSONObject  jsonObject);
}
