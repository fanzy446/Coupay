package com.futurePayment.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.futurePayment.constant.Uris;
import android.util.Log;

/**
 * 
 * @author luo
 * 
 */
public class MyHttpClient {
	private static int OK = 200;// OK: Success!
	private HttpClient http = new DefaultHttpClient();
	private String name;

	public MyHttpClient(String name) {
		// ����ʱ
		http.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				5000);
		// ��ȡ��ʱ
		http.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		this.name = name;
	}

	/**
	 * 
	 * @param serviceType
	 *            ��������
	 * @param json
	 *            ���͵�JSON����
	 * @return ����������
	 */
	public MyResponse post(int serviceType, JSONObject json) {
		MyResponse response = null;
		HttpPost post = new HttpPost(Uris.BASIC_URL);
		try {
			JSONObject set = new JSONObject();
			set.put("serviceType", serviceType);
			if (name != null) {
				set.put("name", name);
			}
			if (json != null) {
				set.put("data", json);
			}
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			// String requestJson = AES.encrypt(json.toString());
			params.add(new BasicNameValuePair("json", set.toString()));
			post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			Log.i("test", "request:" + params.toString());
			HttpResponse res = http.execute(post);
			int statusCode = res.getStatusLine().getStatusCode();
			Log.i("test", "statusCode:" + statusCode);
			if (statusCode == OK) {
				JSONTokener parser = new JSONTokener(EntityUtils.toString(
						res.getEntity(), "utf-8"));
				response = new MyResponse((JSONObject) parser.nextValue());
			} else {
				JSONObject temp = new JSONObject();
				temp.put("result", statusCode);
				response = new MyResponse(temp);
			}
			Log.i("test", "response:" + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * ����url��ַ��inputstream��ʽ����
	 * 
	 * @param urlStr
	 *            url��ַ
	 * @return ���
	 */
	public InputStream getInputStreamFromUrl(String urlStr) {
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			inputStream = urlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(
					getInputStreamFromUrl(urlStr)));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
