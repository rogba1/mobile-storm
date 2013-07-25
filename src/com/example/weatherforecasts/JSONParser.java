package com.example.weatherforecasts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			 try {
		            jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
		        } catch (Exception e0) {
		            Log.e("JSON Parser0", "Error parsing data [" + e0.getMessage()+"] "+json);
		            Log.e("JSON Parser0", "Error parsing data " + e0.toString());
		            try {
		                jObj = new JSONObject(json.substring(1));
		            } catch (Exception e1) {
		                Log.e("JSON Parser1", "Error parsing data [" + e1.getMessage()+"] "+json);
		                Log.e("JSON Parser1", "Error parsing data " + e1.toString());
		                try {
		                    jObj = new JSONObject(json.substring(2));
		                } catch (Exception e2) {
		                    Log.e("JSON Parser2", "Error parsing data [" + e2.getMessage()+"] "+json);
		                    Log.e("JSON Parser2", "Error parsing data " + e2.toString());
		                    try {
		                        jObj = new JSONObject(json.substring(3));
		                    } catch (Exception e3) {
		                        Log.e("JSON Parser3", "Error parsing data [" + e3.getMessage()+"] "+json);
		                        Log.e("JSON Parser3", "Error parsing data " + e3.toString());
		                    }
		                }
		            }
		        }
		}

		// return JSON String
		return jObj;

	}
	
	public JSONArray getJSONArrayfromURL(String url){
	    InputStream is = null;
	    String result = "";
	    JSONArray jArray = null;

	    try {
	    DefaultHttpClient httpclient = new DefaultHttpClient();
	            HttpGet httpget = new HttpGet(url);
	            HttpResponse response = httpclient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

	    try {
	        jArray = new JSONArray(result);     
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

	    return jArray;
	}
}
