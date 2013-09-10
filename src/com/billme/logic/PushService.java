package com.billme.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.LinkedList;

import org.json.JSONObject;
import org.json.JSONTokener;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class PushService extends Service implements Runnable{

	private String serverUrl = "http://110.64.89.205"; // the url of the server
	private final int serverPost = 5000; // the post of the server
	private final int INTERVAL = 1000 * 30; // interval is set to 30 seconds
	private LinkedList<MyMessage> unReadMessage = new LinkedList<MyMessage>();
	private final String ACTION = "PushService";
	private PushMessageReceiver receiver = null;
	public boolean isRun = true;
	
	@Override
	public void onCreate() {
		Log.i("error", "推送服务初始化中");
		super.onCreate();
		this.isRun = true;
		registerReceiver(receiver, new IntentFilter(ACTION));
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("error", "推送服务启动中");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Socket socket;
			try {
				Thread.sleep(INTERVAL);
				socket = new Socket(serverUrl, serverPost);
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());
				out.writeUTF(MainService.getUser().getName());
				DataInputStream in = new DataInputStream(
						socket.getInputStream());

				String hasMessage = in.readUTF();
				// check to see if there is unread message
				if (hasMessage != null && hasMessage.equals("yes")) {
					boolean done = false;
					while (!done) {
						String messageString = in.readUTF();
						if (messageString != null) {
							if (messageString.equals("done!"))
								break;
							else {
								MyMessage message = getMessage(messageString);
								unReadMessage.add(message);
							}
						}
					}
				}
				socket.close();
				Log.i("error", unReadMessage.toString());
				for(int i = 0; i < unReadMessage.size(); i ++)
				{
					 Intent intent = new Intent(ACTION);
					 intent.putExtra("message", unReadMessage.get(i));
					 sendBroadcast(intent);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.isRun = false;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public int getServerPost() {
		return serverPost;
	}

	public int getINTERVAL() {
		return INTERVAL;
	}

	public MyMessage getMessage(String messageString) {
		JSONTokener parser = new JSONTokener(messageString);
		MyMessage message = new MyMessage();
		try {
			JSONObject json = (JSONObject) parser.nextValue();
			message.setPushType(json.getInt("pushType"));
			message.setData(json.getString("data"));
			message.setDate(json.getString("date"));
		} catch (Exception e) {
			Log.i("error", e.toString());
		}
		return message;
	}
}
