package com.xrl.chexian.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.xrl.chexian.Settings;
import com.xrl.chexian.model.City;
import com.xrl.chexian.utils.gson.GsonUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;


/**
 * 用于数据存储
 * 
 * @author Administrator
 * 
 */
public class SharedPreferencesUtils {

	private static SharedPreferencesUtils instance;

	public static SharedPreferencesUtils getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtils(context);
		}
		return instance;
	}

	private SharedPreferences settings;

	private static final boolean DEBUG = Settings.DEBUG;
	public static final String CITY = "city";
	
	
	public SharedPreferencesUtils(Context context) {
		settings = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void set(String key, String value) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String get(String key) {
		return settings.getString(key, "");
	}

	
	/**
	 * 获取城市
	 * @return
	 */
	public City getCity() {
		String cityString = get(SharedPreferencesUtils.CITY);
		City city = null;
		if (!"".equals(city) && city != null) {
			try {
				city = GsonUtils.getInstance().json2bean(cityString,City.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(DEBUG )e.printStackTrace();
			}
		}
		return city;
	}
}
