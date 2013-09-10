package com.billme.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.billme.ui.*;
import com.futurePayment.constant.PushType;
import com.futurePayment.model.ImageHelper;

/**
 * Push消息处理receiver
 */
public class PushMessageReceiver extends BroadcastReceiver {
	private static int NOTIFY_ID = 0;

	// AlertDialog.Builder builder;

	/**
	 * 
	 * 
	 * @param context
	 *            Context
	 * @param intent
	 *            接收的intent
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {

		Log.i("error", ">>> Receive intent: \r\n" + intent);

		MyMessage myMessage = intent.getParcelableExtra("message");
		NOTIFY_ID++;
		// 更新通知栄1�7
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		ImageHelper imageHelper = ImageHelper.getInstance();
		Intent newIntent = new Intent();
		Notification.Builder mNotificationBuilder = new Notification.Builder(
				context).setSmallIcon(R.drawable.back)
				.setLargeIcon(imageHelper.GetBitmapByUrl(MainService.getUser().getHead()))
		// .setLargeIcon(headBm)
				.setWhen(System.currentTimeMillis());
		JSONObject data;
		try {
			data = new JSONObject(myMessage.getData());

			switch (myMessage.getPushType()) {
			case PushType.RECEIVE_MONEY: {
				newIntent.setClass(context, TradeRecordActivity.class);
				mNotificationBuilder.setTicker("收到来自 " + data.getString("name")
						+ " 的汇款" + data.getDouble("money") + "元");
				mNotificationBuilder.setContentTitle("收到来自 "
						+ data.getString("name") + " 的汇款"
						+ data.getDouble("money") + "元");
				mNotificationBuilder.setContentText("收到来自 "
						+ data.getString("name") + " 的汇款"
						+ data.getDouble("money") + "元");
				break;
			}
			case PushType.MUTIPLE_PAY: {
				newIntent.setClass(context, PaymentActivity.class);
				mNotificationBuilder.setTicker(data.getString("sponsor")
						+ "请求多人支付");
				mNotificationBuilder.setContentTitle(data.getString("sponsor")
						+ "请求多人支付");
				mNotificationBuilder.setContentText("总共"
						+ data.getInt("number") + "人参与，您需向 "
						+ data.getString("receiver") + " 支付"
						+ data.getDouble("money") + "元");
				newIntent.putExtra("receiver", data.getString("receiver"));
				newIntent.putExtra("money", data.getDouble("money"));
				break;
			}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				newIntent, 0);
		// // Creates an explicit intent for an Activity in your app
		// Intent resultIntent = new Intent(context, ResultActivity.class);
		//
		// // The stack builder object will contain an artificial back stack for
		// the
		// // started Activity.
		// // This ensures that navigating backward from the Activity leads out
		// of
		// // your application to the Home screen.
		// TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// // Adds the back stack for the Intent (but not the Intent itself)
		// stackBuilder.addParentStack(ResultActivity.class);
		// // Adds the Intent that starts the Activity to the top of the stack
		// stackBuilder.addNextIntent(resultIntent);
		// PendingIntent resultPendingIntent =
		// stackBuilder.getPendingIntent(
		// 0,
		// PendingIntent.FLAG_UPDATE_CURRENT
		// );
		// mBuilder.setContentIntent(resultPendingIntent);

		mNotificationBuilder.setContentIntent(contentIntent);

		Notification n = mNotificationBuilder.build();
		n.flags |= Notification.FLAG_NO_CLEAR;
		n.defaults |= Notification.DEFAULT_VIBRATE;
		mNotificationManager.notify(NOTIFY_ID, n);
	}
}
// if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
// // 获取消息内容
// String message = intent.getExtras().getString(
// PushConstants.EXTRA_PUSH_MESSAGE_STRING);
// try {
// showNotify(context, new JSONObject(message));
// } catch (JSONException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// // // 消息的用户自定义内容读取方式
// // Log.i(TAG, "onMessage: " + message);
// //
// // // 用户在此自定义处理消恄1�7�1�7以下代码为demo界面展示甄1�7�1�7
// // Intent responseIntent = null;
// // responseIntent = new Intent(Utils.ACTION_MESSAGE);
// // responseIntent.putExtra(Utils.EXTRA_MESSAGE, message);
// // responseIntent.setClass(context, PushDemoActivity.class);
// // responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// // context.startActivity(responseIntent);
//
// } else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE)) {
// // 处理绑定等方法的返回数据
// // PushManager.startWork()的返回�1ￄ1�7��1ￄ1�7�过PushConstants.METHOD_BIND得到
//
// // 获取方法
// final String method = intent
// .getStringExtra(PushConstants.EXTRA_METHOD);
// // 方法返回错误码�1ￄ1�7�若绑定返回错误（非0），则应用将不能正常接收消息〄1�7�1�7
// // 绑定失败的原因有多种，如网络原因，或access token过期〄1�7�1�7
// // 请不要在出错时进行简单的startWork调用，这有可能导致死循环〄1�7�1�7
// // 可以通过限制重试次数，或者在其他时机重新调用来解决�1ￄ1�7�1�7�1�7
// final int errorCode = intent
// .getIntExtra(PushConstants.EXTRA_ERROR_CODE,
// PushConstants.ERROR_SUCCESS);
// // 返回内容
// final String content = new String(
// intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));
//
// // 用户在此自定义处理消恄1�7�1�7以下代码为demo界面展示甄1�7�1�7
// Log.i("error", "onMessage: method : " + method);
// Log.i("error", "onMessage: result : " + errorCode);
// Log.i("error", "onMessage: content : " + content);
//
// paraseContent(context, errorCode, content);
// // Toast.makeText(
// // context,
// // "method : " + method + "\n result: " + errorCode
// // + "\n content = " + content, Toast.LENGTH_SHORT)
// // .show();
// //
// // Intent responseIntent = null;
// // responseIntent = new Intent(Utils.ACTION_RESPONSE);
// // responseIntent.putExtra(Utils.RESPONSE_METHOD, method);
// // responseIntent.putExtra(Utils.RESPONSE_ERRCODE, errorCode);
// // responseIntent.putExtra(Utils.RESPONSE_CONTENT, content);
// // responseIntent.setClass(context, PushDemoActivity.class);
// // responseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// // context.startActivity(responseIntent);
//
// // 可�1ￄ1�7��1ￄ1�7��1ￄ1�7�知用户点击事件处理
// } else if (intent.getAction().equals(
// PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK)) {
// // Log.d(TAG, "intent=" + intent.toUri(0));
// //
// // Intent aIntent = new Intent();
// // aIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// // aIntent.setClass(context, CustomActivity.class);
// // String title = intent
// // .getStringExtra(PushConstants.EXTRA_NOTIFICATION_TITLE);
// // aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_TITLE, title);
// // String content = intent
// // .getStringExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT);
// // aIntent.putExtra(PushConstants.EXTRA_NOTIFICATION_CONTENT,
// // content);
// //
// // context.startActivity(aIntent);
// }
// }
//
// private void showNotify(Context context, JSONObject message) {
// // TODO Auto-generated method stub
// NOTIFY_ID++;
// // 更新通知栄1�7
// NotificationManager mNotificationManager = (NotificationManager) context
// .getSystemService(Context.NOTIFICATION_SERVICE);
// Intent intent = new Intent();
// Notification.Builder mNotificationBuilder = new Notification.Builder(
// context).setSmallIcon(R.drawable.back)
// // .setLargeIcon(headBm)
// .setWhen(System.currentTimeMillis());
//
// try {
// switch (message.getInt("pushType")) {
// case PushType.MUTIPLE_PAY: {
// intent.setClass(context, PaymentActivity.class);
// JSONObject data = message.getJSONObject("data");
// mNotificationBuilder.setTicker(data.getString("sponsor")
// + "请求多人支付");
// mNotificationBuilder.setContentTitle(data.getString("sponsor")
// + "请求多人支付");
// mNotificationBuilder.setContentText("总共"
// + data.getInt("number") + "人参与，您需各1�7 "
// + data.getString("receiver") + " 支付"
// + data.getDouble("money") + "兄1�7");
// intent.putExtra("receiver", data.getString("receiver"));
// intent.putExtra("money", data.getDouble("money"));
// }
// break;
// case PushType.RECEIVE_MONEY: {
// intent.setClass(context, TradeRecordActivity.class);
// JSONObject data = message.getJSONObject("data");
// mNotificationBuilder.setTicker("收到来自 " + data.getString("name")
// + " 的汇欄1�7" + data.getDouble("money") + "兄1�7");
// mNotificationBuilder.setContentTitle("收到来自 "
// + data.getString("name") + " 的汇欄1�7"
// + data.getDouble("money") + "兄1�7");
// mNotificationBuilder.setContentText("收到来自 "
// + data.getString("name") + " 的汇欄1�7"
// + data.getDouble("money") + "兄1�7");
// }
// break;
// }
// } catch (JSONException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
// intent, 0);
// // // Creates an explicit intent for an Activity in your app
// // Intent resultIntent = new Intent(context, ResultActivity.class);
// //
// // // The stack builder object will contain an artificial back stack for
// // the
// // // started Activity.
// // // This ensures that navigating backward from the Activity leads out
// // of
// // // your application to the Home screen.
// // TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// // // Adds the back stack for the Intent (but not the Intent itself)
// // stackBuilder.addParentStack(ResultActivity.class);
// // // Adds the Intent that starts the Activity to the top of the stack
// // stackBuilder.addNextIntent(resultIntent);
// // PendingIntent resultPendingIntent =
// // stackBuilder.getPendingIntent(
// // 0,
// // PendingIntent.FLAG_UPDATE_CURRENT
// // );
// // mBuilder.setContentIntent(resultPendingIntent);
//
// mNotificationBuilder.setContentIntent(contentIntent);
//
// Notification n = mNotificationBuilder.build();
// n.flags |= Notification.FLAG_NO_CLEAR;
// n.defaults |= Notification.DEFAULT_VIBRATE;
// mNotificationManager.notify(NOTIFY_ID, n);
// // 通知丄1�7下才会生效哦
// }
//
// // /**
// // * 处理登录结果
// // *
// // * @param errorCode
// // * @param content
// // */
// // private void paraseContent(final Context context, int errorCode,
// // String content) {
// // // TODO Auto-generated method stub
// // if (errorCode == 0) {
// // String appid = "";
// // String channelid = "";
// // String userid = "";
// //
// // try {
// // JSONObject jsonContent = new JSONObject(content);
// // JSONObject params = jsonContent
// // .getJSONObject("response_params");
// // appid = params.getString("appid");
// // channelid = params.getString("channel_id");
// // userid = params.getString("user_id");
// // } catch (JSONException e) {
// // Log.e("error", "Parse bind json infos error: " + e);
// // }
// // Log.i("error", "百度云登陆成功：" + "appid-->" + appid + "channelid-->"
// // + channelid + "userid-->" + userid);
// // // SharePreferenceUtil util = PushApplication.getInstance()
// // // .getSpUtil();
// // // util.setAppId(appid);
// // // util.setChannelId(channelid);
// // // util.setUserId(userid);
// // } else {
// // if (errorCode == 30607) {
// // Log.i("error", "账号已过期，请重新登彄1�7");
// // // 跳转到重新登录的界面
// // } else {
// // Log.i("error", "启动失败，正在重评1�7...");
// // new Handler().postDelayed(new Runnable() {
// //
// // @Override
// // public void run() {
// // // TODO Auto-generated method stub
// // PushManager
// // .startWork(context,
// // PushConstants.LOGIN_TYPE_API_KEY,
// // Utils.API_KEY);
// // }
// // }, 2000);// 两秒后重新开始验评1�7
// // }
// // }
// // }
// }