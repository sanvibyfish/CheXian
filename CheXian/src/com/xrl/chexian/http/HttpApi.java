package com.xrl.chexian.http;


import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import com.xrl.chexian.model.Result;


public interface HttpApi {
	 abstract public Result doHttpRequest(HttpRequestBase httpRequest,
			 Class clazz) throws Exception;
	 
	    abstract public String doHttpPost(String url, NameValuePair... nameValuePairs)
	            throws Exception;

	    abstract public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs);

	    abstract public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs);
}
