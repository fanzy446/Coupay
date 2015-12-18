package com.test;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpTest {
	private static HttpClient http = new HttpClient();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject jobj = new JSONObject();
		jobj.put("name", "lgb");
		jobj.put("serviceType", -202);
		JSONObject data = new JSONObject();
		data.put("name", "a");
		data.put("loginPassword", "a");
		data.put("payPassword", "a");
//		data.put("sex", "ÄÐ");
		data.put("birthday", "2013-9-1");
		jobj.put("data", data);
		System.out.println(jobj.toString());
		JSONObject j = JSONObject.fromObject(jobj.toString());
		JSONObject o = j.getJSONObject("data");
		System.out.println(o.toString());
		post(jobj);
	}
	
	public static void post(JSONObject json) {
		PostMethod post = new PostMethod("http://localhost:8080/ssh/topService");
		NameValuePair request = new NameValuePair( "json",json.toString());
		post.setRequestBody( new NameValuePair[] {request});
		try {
			http.executeMethod(post);
			System.out.println(post.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
