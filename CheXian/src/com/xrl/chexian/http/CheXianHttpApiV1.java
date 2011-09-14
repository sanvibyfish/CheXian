package com.xrl.chexian.http;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.xrl.chexian.Settings;

import android.util.Log;


public class CheXianHttpApiV1 {
	private static final String TAG = "MaiMaiJiaHttpApiV2"; 
	private final DefaultHttpClient mHttpClient = AbstractHttpApi
			.createHttpClient();
	private HttpApi mHttpApi;
	private final String mApiBaseUrl;
	private final AuthScope mAuthScope;
	private static final boolean DEBUG = Settings.DEBUG;

	private static CheXianHttpApiV1 instance = null;
	
	private static final String URL_API_REGION_LIST = "/";
	
	
//	private static final int pageSize = 10;
	
	public static CheXianHttpApiV1 getInstance(){
		if(instance == null){
			instance = new CheXianHttpApiV1(Settings.DOMAIN, Settings.PORT, Settings.CLIENT_VERSION);
		}
		return instance;
	}
	
	
	public static String REGION_MOBILE_LIST = "RegionMobileList";
	/**
	 * 
	 * @param domain
	 * @param port
	 * @param clientVersion
	 */
	public CheXianHttpApiV1(String domain, int port, String clientVersion) {
		mApiBaseUrl = "http://" + domain + ":" + port ;
		mAuthScope = new AuthScope(domain, port);

		mHttpApi = new HttpApiWithOAuth(mHttpClient, clientVersion);
	}
	
    

}
