package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.billme.widget.MySurroundAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.EnterpriseBasicInfo;
import com.futurePayment.model.Friend;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SearchEnterpriseActivity extends BaseActivity implements
		BillMeActivity {
	public static final int SEARCH_ENTERPRISE_SUCCESS = 1;
	public static final int SEARCH_ENTERPRISE_FAILURE = -1;
	public static final int ATTENT_ENTERPRISE_SUCCESS = 2;
	public static final int ATTENT_ENTERPRISE_FAILURE = -2;

	private EditText name = null;
	private ImageButton searchButton = null;
	private ProgressDialog pd = null;
	private ListView list = null;
	private MySurroundAdapter adapter = null;
	private LinkedList<EnterpriseBasicInfo> el = null;
	private EnterpriseBasicInfo choice = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_enterprise);

		name = (EditText) findViewById(R.id.et_search_enterprise_name);
		searchButton = (ImageButton) findViewById(R.id.ib_search_enterprise_search);
		list = (ListView) findViewById(R.id.lv_search_enterprise_list);

		searchButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pd == null) {
					pd = new ProgressDialog(SearchEnterpriseActivity.this);
				}
				pd.setMessage("Loading..");
				pd.show();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", name.getText().toString());
				Task task = new Task(Task.TASK_SEARCH_ENTERPRISE, map);
				MainService.newTask(task);
			}

		});
	}

	private void bindAdapter(LinkedList<EnterpriseBasicInfo> temp) {
		el = temp;
		adapter = new MySurroundAdapter(this, el);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				choice = el.get(arg2);
				new AlertDialog.Builder(SearchEnterpriseActivity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("确定关注商家 " + choice.getName() + "?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										if (pd == null) {
											pd = new ProgressDialog(
													SearchEnterpriseActivity.this);
										}
										pd.setMessage("Attenting..");
										pd.show();
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("name", name.getText()
												.toString());
										Task task = new Task(
												Task.TASK_FOLLOW_ENTERPRISE,
												map);
										MainService.newTask(task);
										arg0.dismiss();
									}
								}).setNegativeButton("取消", null).show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
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
		case SEARCH_ENTERPRISE_SUCCESS:
			bindAdapter((LinkedList<EnterpriseBasicInfo>) param[1]);
			pd.cancel();
			break;
		case SEARCH_ENTERPRISE_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.EMPTY:
				hint = "No enterprise";
				pd.cancel();
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
				break;
			default:
				hint = "Error";
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
				break;
			}
		case ATTENT_ENTERPRISE_SUCCESS:
			MainService.getFuturePayment().getUser()
					.appendEnterpriseList(choice);
			Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			break;
		case ATTENT_ENTERPRISE_FAILURE:
			Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
