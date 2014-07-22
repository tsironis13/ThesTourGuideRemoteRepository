package com.example.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class ServiceHandler {

	static String response = null;
	static Bitmap bitmap = null;
    public final static int GET = 1;
    public final static int POST = 2;
 
    public ServiceHandler() {
 
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }
 
    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    
    public Bitmap downloadBitmap(String url){
    	
    	
    	try 
    	{
    		DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpEntity httpEntity = null;
        	HttpResponse httpResponse = null;
        	
        	HttpGet httpGet = new HttpGet(url);
			httpResponse = httpClient.execute(httpGet);
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK){
					Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
	                return null;
				}
				
			httpEntity = httpResponse.getEntity();
			
			 if (httpEntity != null) {
	                InputStream inputStream = null;
	                try {
	                    inputStream = httpEntity.getContent(); 
	                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	                } finally {
	                    if (inputStream != null) {
	                        inputStream.close();  
	                    }
	                    httpEntity.consumeContent();
	                }
	            }
			
		}  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
        }
     return bitmap;	
  }
  
    
    
    
    
    public String makeServiceCall(String url, int method, List<NameValuePair> params) {
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }
                HttpGet httpGet = new HttpGet(url);
 
                httpResponse = httpClient.execute(httpGet);
 
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
 
    }
	
}
