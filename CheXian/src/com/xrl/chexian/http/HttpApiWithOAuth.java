package com.xrl.chexian.http;

import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import com.xrl.chexian.model.Result;


/**
 * HTTP访问
 * @author Joe LaPenna (joe@joelapenna.com)
 *
 */
public class HttpApiWithOAuth extends AbstractHttpApi {
	protected static final Logger LOG = Logger.getLogger(HttpApiWithOAuth.class.getCanonicalName());
    public HttpApiWithOAuth(DefaultHttpClient httpClient, String clientVersion) {
        super(httpClient, clientVersion);
    }

    
    public Result doHttpRequest(HttpRequestBase httpRequest,
    		Class clazz) throws Exception {
        return executeHttpRequest(httpRequest, clazz);
    }

    public String doHttpPost(String url, NameValuePair... nameValuePairs) throws Exception {
        throw new RuntimeException("Haven't written this method yet.");
    }
}
