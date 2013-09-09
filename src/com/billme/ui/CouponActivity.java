package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CouponActivity extends BaseActivity implements BillMeActivity {

	public static final int GET_COUPON_SECCUSS = 1;
	public static final int GET_COUPON_FAILURE = -1;

	private ListView list = null;
	private ProgressDialog pd = null;
	private MyCouponAdapter adapter = null;
	private LinkedList<Coupon> cl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);
		addLayout();

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

//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		// TODO Auto-generated method stub
//		CouponActivity.this.getMenuInflater().inflate(R.menu.pog_fragment_c, menu);
//		MenuItem item = menu.getItem(0);
//		if (MainActivity.show)
//			item.setTitle("通话");
//		else
//			item.setTitle("编辑");
//		super.onCreateContextMenu(menu, v, menuInfo);
//	}
//
//	public boolean onContextItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		AdapterContextMenuInfo infor = (AdapterContextMenuInfo) item
//				.getMenuInfo();
//		switch (item.getItemId()) {
//		case R.id.menu_pog_fragment_call: {
//			if (MainActivity.show) {
//
//			} else {
//				Intent intent = new Intent();
//				intent.setClass(getActivity(), GroupEditor.class);
//				intent.putExtra("id",
//						MainActivity.group_position_id.get(infor.position));
//				startActivityForResult(intent, 2);
//			}
//		}
//			return true;
//		case R.id.menu_pog_fragment_delete: {
//			if (MainActivity.show) {
//				MainActivity.db
//						.deletePerson((int) MainActivity.person_position_id
//								.get(infor.position));
//				MainActivity.person_id_name_number_List.remove(infor.position);
//				personAdapter.notifyDataSetChanged();
//			} else {
//				MainActivity.db
//						.deleteGroup((int) MainActivity.group_position_id
//								.get(infor.position));
//				MainActivity.group_id_name_number_List.remove(infor.position);
//				groupAdapter.notifyDataSetChanged();
//			}
//		}
//			return true;
//		default:
//			return super.onContextItemSelected(item);
//		}
//	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case GET_COUPON_SECCUSS:
			cl = (LinkedList<Coupon>) param[1];
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
