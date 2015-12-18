package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.Friend;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SearchFriendActivity extends BaseActivity implements
		BillMeActivity {
	public static final int SEARCH_FRIEND_SUCCESS = 1;
	public static final int SEARCH_FRIEND_FAILURE = -1;
	public static final int ADD_FRIEND_SUCCESS = 2;
	public static final int ADD_FRIEND_FAILURE = -2;

	private EditText name = null;
	private ImageButton searchButton = null;
	private ProgressDialog pd = null;
	private ListView list = null;
	private MyListViewAdapter adapter = null;
	private ArrayList<HashMap<String, Object>> sl = null;
	private Friend choice = new Friend();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_friend);

		name = (EditText) findViewById(R.id.et_search_friend_name);
		searchButton = (ImageButton) findViewById(R.id.ib_search_friend_search);
		list = (ListView) findViewById(R.id.lv_search_friend_list);

		searchButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (pd == null) {
					pd = new ProgressDialog(SearchFriendActivity.this);
				}
				pd.setMessage("Searching..");
				pd.show();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", name.getText().toString());
				Task task = new Task(Task.TASK_SEARCH_FRIEND, map);
				MainService.newTask(task);
			}

		});
	}

	private void bindAdapter(LinkedList<Friend> fl) {
		sl = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < fl.size(); i++) {
			Friend f = fl.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 加入图片
			map.put("icon", f.getPath());
			map.put("text", f.getName());
			sl.add(map);
		}
		adapter = new MyListViewAdapter(this, sl);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				choice.setName((String) sl.get(arg2).get("text"));
				choice.setPath((String) sl.get(arg2).get("icon"));
				new AlertDialog.Builder(SearchFriendActivity.this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("添加好友 " + choice.getName() + "?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										if (pd == null) {
											pd = new ProgressDialog(
													SearchFriendActivity.this);
										}
										pd.setMessage("Attenting..");
										pd.show();
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("name", name.getText()
												.toString());
										Task task = new Task(
												Task.TASK_ADD_FRIEND, map);
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
		case SEARCH_FRIEND_SUCCESS:
			bindAdapter((LinkedList<Friend>) param[1]);
			pd.cancel();
			break;
		case SEARCH_FRIEND_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.EMPTY:
				hint = "No friend";
				pd.cancel();
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
				break;
			default:
				hint = "Error";
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
				break;
			}
		case ADD_FRIEND_SUCCESS:
			Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			break;
		case ADD_FRIEND_FAILURE:
			MainService.getFuturePayment().getUser().appendFriendList(choice);
			Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}