package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyCouponAdapter;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.BankCard;
import com.futurePayment.model.Coupon;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CouponActivity extends BaseActivity implements BillMeActivity {

	public static final int GET_COUPON_SECCUSS = 1;
	public static final int GET_COUPON_FAILURE = -1;

	private ListView list = null;
	private ProgressDialog pd = null;
	private MyCouponAdapter adapter = null;
	private ArrayList<Coupon> cl = new ArrayList<Coupon>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);

		list = (ListView) findViewById(R.id.lv_coupon_list);
		if (this.pd == null) {
			this.pd = new ProgressDialog(this);
		}
		pd.setMessage("Loading..");
		pd.show();

		Task task = new Task(Task.TASK_GET_COUPON);
		MainService.newTask(task);

	}

	private void bindAdapter() {
		adapter = new MyCouponAdapter(this, cl);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 选中某张优惠券
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_coupon, menu);
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case GET_COUPON_SECCUSS:
			cl = (ArrayList<Coupon>) param[1];
			bindAdapter();
			pd.cancel();
			break;
		case GET_COUPON_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.EMPTY:
				hint = "No coupon";
				pd.cancel();
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
			}
			break;
		}

	}
}
