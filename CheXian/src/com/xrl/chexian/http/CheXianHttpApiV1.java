package com.xrl.chexian.http;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.xrl.chexian.Settings;
import com.xrl.chexian.model.Group;
import com.xrl.chexian.model.ModelQuery;

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

	private static final String URL_API_MODEL_QUERY = "/ebusiness/auto/ris/combo-model-query.do";

	// private static final int pageSize = 10;

	public static CheXianHttpApiV1 getInstance() {
		if (instance == null) {
			instance = new CheXianHttpApiV1(Settings.DOMAIN, Settings.PORT,
					Settings.CLIENT_VERSION);
		}
		return instance;
	}

	private String fullUrl(String url) {
		return mApiBaseUrl + url;
	}

	/**
	 * 
	 * @param domain
	 * @param port
	 * @param clientVersion
	 */
	public CheXianHttpApiV1(String domain, int port, String clientVersion) {
		mApiBaseUrl = "http://" + domain + ":" + port;
		mAuthScope = new AuthScope(domain, port);

		mHttpApi = new HttpApiWithOAuth(mHttpClient, clientVersion);
	}

	// public Group<RegionMobile> getRegionMobileList() throws Exception{
	// HttpGet httpGet = mHttpApi.createHttpGet(fullUrl(URL_API_MODEL_QUERY),
	// new BasicNameValuePair("output", Settings.OUTPUT));
	// Group<RegionMobile> cities = (Group<RegionMobile>)
	// mHttpApi.doHttpRequest(httpGet, RegionMobile.class);
	// if(DEBUG)Log.d(TAG, "city size:" + cities.size());
	// return cities;
	// }

	public ModelQuery getModelQuery(String licenseNo, String registerDate,
			String model, String bizQuoteBeginDate, String forceQuoteBeginDate)
			throws Exception {
		int bizQuoteBeginDateType = 0;
		if (!"".equals(bizQuoteBeginDate)
				&& bizQuoteBeginDate.equals(forceQuoteBeginDate)) {
			bizQuoteBeginDateType = 1;
		}
		HttpGet httpGet = mHttpApi
				.createHttpGet(fullUrl(URL_API_MODEL_QUERY),new BasicNameValuePair("responseProtocol",Settings.OUTPUT),
						new BasicNameValuePair("debug", String.valueOf(Settings.DEBUG)),
						new BasicNameValuePair("vehicle.licenseNo", licenseNo),
						new BasicNameValuePair("vehicle.registerDate",registerDate),
						new BasicNameValuePair("vehicle.model", model),
						new BasicNameValuePair("bizQuote.beginDate",bizQuoteBeginDate), 
						new BasicNameValuePair("bizQuote.forceQuote", forceQuoteBeginDate),
						new BasicNameValuePair("bizQuote.useBizBeginDate",String.valueOf(bizQuoteBeginDateType)),
						new BasicNameValuePair("bizQuote.beginDate",bizQuoteBeginDate));
		ModelQuery modelQuery = (ModelQuery) mHttpApi.doHttpRequest(httpGet,
				ModelQuery.class);
		return modelQuery;
	}

}
