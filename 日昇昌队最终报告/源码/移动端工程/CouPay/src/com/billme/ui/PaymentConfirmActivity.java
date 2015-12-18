package com.billme.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import com.billme.logic.BillMeActivity;

import com.billme.logic.MainService;
import com.billme.widget.MyCouponAdapter;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.Task;
import com.futurePayment.model.Coupon;
import com.futurePayment.model.Friend;
import com.futurePayment.model.ImageHelper;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentConfirmActivity extends BaseActivity implements
		BillMeActivity {
	public final static int APPLY_SUCCESS = 1;
	public final static int APPLY_FAILURE = -1;
	public final static int QUERY_SUCCESS = 2;
	public final static int QUERY_FAILURE = -2;

	private TextView text = null;
	private ListView choiceList = null;
	private GridView peopleList = null;
	private MyListViewAdapter choiceAdapter = null;
	private ArrayList<HashMap<String, Object>> cl = new ArrayList<HashMap<String, Object>>();
	private MyListViewAdapter peopleAdapter = null;
	private ArrayList<HashMap<String, Object>> pl = new ArrayList<HashMap<String, Object>>();
	private ArrayList<Friend> fl = new ArrayList<Friend>();
	private LinearLayout layout = null;
	private Button friendButton = null;
	private Button codeButton = null;
	private ImageView couponImage = null;
	private ProgressDialog pd = null;

	private String receiver;
	private double initialMoney;
	private String method;
	private int couponPos = 0;
	private int couponId = -1;
	boolean mutipay = false;

	private MyCouponAdapter couponAdapter = null;
	private LinkedList<Coupon> ll = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_confirm);
		addTitle("交易确认");

		text = (TextView) findViewById(R.id.tv_payment_confirm_text);
		choiceList = (ListView) findViewById(R.id.lv_payment_confirm_choice);
		peopleList = (GridView) findViewById(R.id.gv_payment_confirm_people);
		layout = (LinearLayout) findViewById(R.id.ll_payment_confirm_layout);
		friendButton = (Button) findViewById(R.id.btn_payment_confirm_friend);
		codeButton = (Button) findViewById(R.id.btn_payment_confirm_code);
		couponImage = (ImageView) findViewById(R.id.iv_payment_confirm_coupon);

		// 得到交易信息并显示。。。
		Intent intent = getIntent();
		String result = intent.getStringExtra("result");
		try {
			JSONObject json = new JSONObject(result);
			receiver = json.getString("receiver");
			initialMoney = json.getDouble("money");
			method = json.getString("method");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		refreshText();
		bindChoiceAdapter();

		codeButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		friendButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> name = new ArrayList<String>();
				for (int i = 0; i < pl.size(); i++) {
					name.add(fl.get(i).getName());
				}
				Intent intent = new Intent();
				intent.putExtra("name", name);
				intent.setClass(PaymentConfirmActivity.this,
						FriendActivity.class);
				startActivityForResult(intent, 0);
			}

		});
		couponImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pd == null) {
					pd = new ProgressDialog(PaymentConfirmActivity.this);
				}
				pd.setMessage("Loading..");
				pd.show();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", receiver);
				map.put("money", initialMoney);
				Task task = new Task(Task.TASK_GET_AVAILABLE_COUPON, map);
				MainService.newTask(task);
			}

		});
		// new AlertDialog.Builder(this).setTitle("是否使用优惠券")
		// .setPositiveButton("是", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// if (pd == null) {
		// pd = new ProgressDialog(PaymentConfirmActivity.this);
		// }
		// pd.setMessage("Loading..");
		// pd.show();
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("name", receiver);
		// map.put("money", initialMoney);
		// Task task = new Task(Task.TASK_GET_COUPON, map);
		// MainService.newTask(task);
		// }
		//
		// }).setNegativeButton("否", null).create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_payment_confirm, menu);
		return true;
	}

	private void bindChoiceAdapter() {
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("text", "单人支付");
		map1.put("end", R.drawable.nav_left);
		cl.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("text", "多人支付");
		map2.put("end", R.drawable.nav_left);
		cl.add(map2);
		choiceAdapter = new MyListViewAdapter(this, cl);
		choiceList.setAdapter(choiceAdapter);
		choiceList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					// 选中单人支付
					// 跳转页面
					Intent intent = new Intent();
					// 传入付款人

					// 传入收款人
					intent.putExtra("receiver", receiver);
					// 传入金额
					intent.putExtra("money", getMoney());
					intent.putExtra("method", method);
					intent.putExtra("couponId", couponId);
					intent.setClass(PaymentConfirmActivity.this,
							PaymentActivity.class);
					startActivity(intent);
					break;
				case 1:
					if (mutipay == false)
					// 选中多人支付
					{
						cl.get(1).put("end", R.drawable.nav_left);
						peopleList.setVisibility(View.VISIBLE);
						layout.setVisibility(View.VISIBLE);
						mutipay = true;
						choiceAdapter.notifyDataSetChanged();
					} else {
						if (pd == null) {
							pd = new ProgressDialog(PaymentConfirmActivity.this);
						}
						pd.setMessage("Sending..");
						pd.show();
						HashMap<String, Object> param = new HashMap<String, Object>();
						ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < pl.size(); i++) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("name", fl.get(i).getName());
							// 分摊方式需要考虑
							map.put("money", getMoney());
							tempList.add(map);
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("name", MainService.getUser().getName());
						// 分摊方式需要考虑
						map.put("money", getMoney());
						tempList.add(map);
						param.put("sender", tempList);
						Task task = new Task(Task.TASK_MULTI_USER_PAY, param);
						MainService.newTask(task);
					}
				}
			}

		});
	}

	private void bindPeopleAdapter() {
		// HashMap<String, Object> map3 = new HashMap<String, Object>();
		// map3.put("icon", R.drawable.back);
		// pl.add(map3);
		peopleAdapter = new MyListViewAdapter(this, pl);
		peopleList.setAdapter(peopleAdapter);
		peopleList.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// if(arg2 == pl.size() - 1)
				// {
				// //添加好友
				// }
				// else
				// {
				pl.remove(arg2);
				fl.remove(arg2);
				refreshText();
				peopleAdapter.notifyDataSetChanged();
				// }
			}
		});
	}

	private void refreshText()
	{
		text.setText("本次消费需向" + receiver + "支付" + getMoney() + "元");
	}
	private double getMoney() {
		double result = initialMoney;
		if (couponPos != 0) {
			Coupon coupon = ll.get(couponPos);
			if ("discount".equals(coupon.getType())) {
				result = initialMoney * coupon.getValue();
			} else if ("substitute".equals(coupon.getType())) {
				result = initialMoney - coupon.getValue();
			}

		}
		result = result / (fl.size() + 1);
		DecimalFormat df = new DecimalFormat("#.##");    
		return Double.parseDouble(df.format(result));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case APPLY_SUCCESS: {
			Toast.makeText(this, "Send messages successfully",
					Toast.LENGTH_SHORT).show();
			// 跳转页面
			Intent intent = new Intent();
			// 传入收款人
			intent.putExtra("receiver", receiver);
			// 传入金额
			intent.putExtra("money", getMoney());
			intent.putExtra("method", method);
			intent.setClass(PaymentConfirmActivity.this, PaymentActivity.class);
			startActivity(intent);
		}
			break;
		case APPLY_FAILURE: {
			pd.cancel();
			Toast.makeText(this, "Send messages failurily", Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case QUERY_SUCCESS: {
			ll = (LinkedList<Coupon>) param[1];
			Coupon temp = new Coupon();
			temp.setEnterpriseName("不使用");
			ll.add(0, temp);
			couponAdapter = new MyCouponAdapter(PaymentConfirmActivity.this, ll);

			new AlertDialog.Builder(this).setTitle("选择优惠券")
			// .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // TODO Auto-generated method stub
			// if (pd == null) {
			// pd = new ProgressDialog(PaymentConfirmActivity.this);
			// }
			// pd.setMessage("Loading..");
			// pd.show();
			// HashMap<String, Object> map = new HashMap<String, Object>();
			// map.put("name", receiver);
			// map.put("money", getMoney());
			// Task task = new Task(Task.TASK_GET_COUPON, map);
			// MainService.newTask(task);
			// }
			//
			// })
			// .setNegativeButton("取消", null)
					.setAdapter(couponAdapter,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// 选中优惠券
									Coupon coupon = ll.get(which);
									if (which == 0) {
										couponId = -1;
										couponImage
												.setImageResource(R.drawable.back);
									} else {
										// 选中某张优惠券
										couponId = coupon.getCouponId();
										ImageHelper ih = ImageHelper
												.getInstance();
										ih.loadDrawable(coupon.getPicture(),
												couponImage);
									}
									couponPos = which;
									text.setText("本次消费需向" + receiver + "支付"
											+ getMoney() + "元");
								}

							}).create();
		}
			break;
		case QUERY_FAILURE: {
			pd.cancel();
			Toast.makeText(this, "Get coupons failurily", Toast.LENGTH_SHORT)
					.show();
		}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		fl = data.getParcelableArrayListExtra("friend");
		pl = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < fl.size(); i++) {
			Friend temp = fl.get(i);
			HashMap<String, Object> map3 = new HashMap<String, Object>();
			// 查找放置头像
			map3.put("text", temp.getName());
			map3.put("icon", temp.getPath());
			pl.add(map3);
		}
		bindPeopleAdapter();
		refreshText();
		// double m = initialMoney - initialMoney / (fl.size() + 1) * fl.size();
		// text.setText("本次消费需向" + receiver + "支付" + m + "元");
	}
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// super.onActivityResult(requestCode, resultCode, data);
	// if (resultCode == RESULT_OK) {
	// Bundle bundle = data.getExtras();
	// String scanResult = bundle.getString("result");
	//
	// String[] tempString = scanResult.split(",");
	// for (int i = 0; i < tempString.length; ++i) {
	// Log.i("error", tempString[i]);
	// }
	// }
	// }
}
