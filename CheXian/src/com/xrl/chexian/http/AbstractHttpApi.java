package com.xrl.chexian.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xrl.chexian.ModelQueryActivity;
import com.xrl.chexian.Settings;
import com.xrl.chexian.error.CheXianException;
import com.xrl.chexian.model.ModelQuery;
import com.xrl.chexian.model.Result;
import com.xrl.chexian.utils.JsonToObjectUtils;
import com.xrl.chexian.utils.StringUtils;
import com.xrl.chexian.utils.gson.GsonUtils;


public abstract class AbstractHttpApi implements HttpApi {
	
	private static final String TAG = "AbstractHttpApi";
	private static final boolean DEBUG = Settings.DEBUG;
	
    private static final String DEFAULT_CLIENT_VERSION = "com.fg114.maimaijia";
    private static final String CLIENT_VERSION_HEADER = "User-Agent";
    private static final int TIMEOUT = 10;
    
    private final DefaultHttpClient mHttpClient;
    private final String mClientVersion;    
    
    public AbstractHttpApi(DefaultHttpClient httpClient, String clientVersion) {
        mHttpClient = httpClient;
        if (clientVersion != null) {
            mClientVersion = clientVersion;
        } else {
            mClientVersion = DEFAULT_CLIENT_VERSION;
        }
    }
    
    public Result executeHttpRequest(HttpRequestBase httpRequest,
    		Class clazz) throws Exception {
        InputStream is = executeHttpRequestSuccess(httpRequest);
        String responseString =  StringUtils.convertStreamToString(is);
        JSONObject jsonResponse = new JSONObject(responseString);
        if(DEBUG)Log.d(TAG,"responseString:" + responseString);
        int  resultCode = jsonResponse.getInt("resultCode");
        switch (resultCode) {
		case 11:
			throw new CheXianException("", "你的客户端版本过低");
		case 12:
			throw new CheXianException("", "你所选择的机构不存在或未开通网销");
		case 13:
			throw new CheXianException("","选择机构不支持此客户端类型");
		case 561:
			
		case 0:
			ModelQuery mq = GsonUtils.getInstance().json2bean(responseString, clazz);
			return mq;
		default:
			
		}
//        if(succeeded){
//        	if(jsonResponse.has("successResult")){
//        		if(DEBUG)Log.d(TAG, "successResult:\n" + jsonResponse.getString("successResult"));
//        		Object obj = jsonResponse.get("successResult");
//        		if(obj instanceof JSONObject){
//        			JSONObject successResultObject = jsonResponse.getJSONObject("successResult");  
//        			return  JsonToObjectUtils.getInstance().convertResult(successResultObject, clazz);
//        		}else if(obj instanceof JSONArray){
//        			JSONArray successResultJSONArray = jsonResponse.getJSONArray("successResult");  
//        			return  JsonToObjectUtils.getInstance().convertResult(successResultJSONArray, clazz);
//        		}
//        	}else{
//        		return null;
//        	}
//        }else{
//        	String message = jsonResponse.getString("message");
//        	throw new CheXianException("", message);
//        }
		return null;
    }
    public InputStream executeHttpRequestSuccess(HttpRequestBase httpRequest) throws Exception{
    	 HttpResponse response = executeHttpRequest(httpRequest);
    	 int statusCode = response.getStatusLine().getStatusCode();
         switch (statusCode) {
         case 200:
            return response.getEntity().getContent();             
         case 400:
         	if(DEBUG)Log.d(TAG, "HTTP Code: 400");
             throw new Exception(
                     EntityUtils.toString(response.getEntity()));

         case 401:
             response.getEntity().consumeContent();
             if(DEBUG)Log.d(TAG, "HTTP Code: 401");
             throw new Exception(response.getStatusLine().toString());

         case 404:
             response.getEntity().consumeContent();
             if(DEBUG)Log.d(TAG, "HTTP Code: 404");
             throw new Exception(response.getStatusLine().toString());

         case 500:
             response.getEntity().consumeContent();
             if(DEBUG)Log.d(TAG, "HTTP Code: 500");
             throw new Exception("Consuetude is down. Try again later.");

         default:
         	 if(DEBUG)Log.d(TAG, "Default case for status code reached: "
                     + response.getStatusLine().toString());
             response.getEntity().consumeContent();
             throw new Exception("Error connecting to MaimaiJia: " + statusCode + ". Try again later.");
     }
    }
    
    public String doHttpPost(String url, NameValuePair... nameValuePairs)
            throws Exception,
            IOException {
        HttpPost httpPost = createHttpPost(url, nameValuePairs);

        HttpResponse response = executeHttpRequest(httpPost);

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                try {
                    return EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                    throw new Exception(e.getMessage());
                }

            case 401:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            case 404:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());

            default:
                response.getEntity().consumeContent();
                throw new Exception(response.getStatusLine().toString());
        }
    }

    /**
     * execute() an httpRequest catching exceptions and returning null instead.
     *
     * @param httpRequest
     * @return
     * @throws IOException
     */
    public HttpResponse executeHttpRequest(HttpRequestBase httpRequest) throws IOException {
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
            return mHttpClient.execute(httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw e;
        }
    }

	@Override
	public HttpGet createHttpGet(String url, List<NameValuePair> nameValuePairs) {
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
        HttpGet httpGet = new HttpGet(url + "?" + query);
        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30 ChromePlus/1.6.3.0");
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Cache-Control","max-age=0");
        return httpGet;
	}
	
    public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
        return createHttpGet(url, stripNulls(nameValuePairs));
    }

    public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(stripNulls(nameValuePairs), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new IllegalArgumentException("Unable to encode http parameters.");
        }
        return httpPost;
    }

    private List<NameValuePair> stripNulls(NameValuePair... nameValuePairs) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < nameValuePairs.length; i++) {
            NameValuePair param = nameValuePairs[i];
            if (param.getValue() != null && !param.getValue().equals("")) {
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Create a thread-safe client. This client does not do redirecting, to allow us to capture
     * correct "error" codes.
     *
     * @return HttpClient
     */
    public static final DefaultHttpClient createHttpClient() {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));

        // Set some client http client parameter defaults.
        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);

        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpParams,
                supportedSchemes);
        return new DefaultHttpClient(ccm, httpParams);
    }

    /**
     * Create the default HTTP protocol parameters.
     */
    private static final HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();

        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);

        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        return params;
    }

}
