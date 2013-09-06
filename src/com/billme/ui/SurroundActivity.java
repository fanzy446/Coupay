package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.billme.widget.MySocietyAdapter;
import com.billme.widget.MySurroundAdapter;
import com.futurePayment.constant.Task;
import com.futurePayment.model.CommentInfo;
import com.futurePayment.model.EnterpriseBasicInfo;
import com.futurePayment.model.Friend;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SurroundActivity extends BaseActivity implements BillMeActivity {
	public static final int GET_SECCUSS = 1;
	public static final int GET_FAILURE = -1;
	public static final int INITIAL_SECCUSS = 2;
	public static final int INITIAL_FAILURE = -2;

	private ImageButton jumpButton = null;
	private ImageButton refreshButton = null;
	private ArrayList<EnterpriseBasicInfo> el = new ArrayList<EnterpriseBasicInfo>();
	private MySurroundAdapter enterpriseAdapter = null;
	private ListView enterpriseList = null;
	private ProgressDialog pd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surround);

		jumpButton = (ImageButton) findViewById(R.id.ib_surround_jump);
		refreshButton = (ImageButton) findViewById(R.id.ib_surround_refresh);
		enterpriseList = (ListView) findViewById(R.id.lv_surround_list);

		jumpButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SurroundActivity.this, SocietyActivity.class);
				startActivity(intent);
			}

		});
		refreshButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (pd == null) {
					pd = new ProgressDialog(SurroundActivity.this);
				}
				pd.setMessage("Loading..");
				pd.show();
				Task task = new Task(Task.TASK_GET_AROUND_ENTERPRISE_INFO, null);
				MainService.newTask(task);
			}

		});
		bindAdapter();
		
	}

	private void bindAdapter() {
		enterpriseAdapter = new MySurroundAdapter(this, el);
		enterpriseList.setAdapter(enterpriseAdapter);
		enterpriseList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 选中某个商家
//				Intent intent = new Intent();
//				intent.setClass(SurroundActivity.this, );
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_surround, menu);
		return true;
	}

	protected void onResume() {
		super.onResume();
		this.init();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (pd == null) {
			pd = new ProgressDialog(this);
		}
		pd.setMessage("Loading..");
		pd.show();
		Task task = new Task(Task.INIT_SURROUND, null);
		MainService.newTask(task);
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case GET_FAILURE:{
			// int state = ((PaymentException) param[1]).getResultCode();
			// String hint = null;
			// switch (state) {
			// case ResultCode.WRONG_PASSWORD:
			// hint = "Password Wrong";
			// break;
			// case ResultCode.REGISTER_NOT_EXISTS:
			// hint = "User Not Exits";
			// break;
			// case ResultCode.REPEAT_LOGIN:
			// hint = "Repeatly Login";
			// break;
			// default:
			// hint = "Unknown fault";
			// break;
			// }
			pd.cancel();
			Toast.makeText(this, "refresh unsuccessfully", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		case GET_SECCUSS:{
			pd.cancel();
			el = (ArrayList<EnterpriseBasicInfo>) param[1];
			enterpriseAdapter.notifyDataSetChanged();
			break;
		}
			
		case INITIAL_FAILURE: {
			pd.cancel();
			break;
		}

		case INITIAL_SECCUSS: {
			pd.cancel();
			ArrayList<EnterpriseBasicInfo> fresh = (ArrayList<EnterpriseBasicInfo>) param[1];
			el = fresh;
			enterpriseAdapter.notifyDataSetChanged();
			break;
		}
		}
	}
}
