package com.billme.util;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	public static boolean checkNet(Context context) {
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// �жϵ�ǰ�����Ƿ��Ѿ�����
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// ��һ��URLȡ��һ��ͼƬ
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getImageFromUrl(URL url) {
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			icon = new BitmapDrawable(hc.getInputStream());
			hc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}
}