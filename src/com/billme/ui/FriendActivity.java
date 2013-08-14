package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.Task;
import com.futurePayment.model.BankCard;
import com.futurePayment.model.Friend;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FriendActivity extends BaseActivity implements BillMeActivity
{
	public static final int GET_FRIEND_SUCCESS = 1;
	public static final int GET_FRIEND_FAILURE = -1;
	
	private ListView friendList = null;
	private Button okButton = null;
	private ProgressDialog pd = null;
	private MyListViewAdapter adapter = null;
	private ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
	private LinkedList<String> nameList = new LinkedList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		MainService.allActivities.add(this);
		addTitle("选择好友");
		
		friendList = (ListView)findViewById(R.id.lv_friend_list);
		okButton = (Button)findViewById(R.id.btn_friend_ok);
		
		if (MainService.getFuturePayment().getUser().getFriendList().size() == 0)
		{
			if (this.pd == null)
			{
				this.pd = new ProgressDialog(this);
			}
			pd.setMessage("Loading..");
			pd.show();
			Task task = new Task(Task.TASK_GET_FRIENDS);
			MainService.newTask(task);
		}
		else
		{
			bindAdapter();
		}
		
		okButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void bindAdapter()
	{
		ArrayList<Friend> fl = MainService.getFuturePayment().getUser()
				.getFriendList();
		al = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < fl.size(); i++)
		{
			Friend f = fl.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			//加入图片
			map.put("text", f.getName());
			al.add(map);
		}
		adapter = new MyListViewAdapter(this, al);
		friendList.setAdapter(adapter);
		friendList.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				// TODO Auto-generated method stub
				//选中某位好友
				String name = (String)al.get(arg2).get("text");
				if(nameList.contains(name))
				{
					al.get(arg2).put("back", null);
					nameList.remove(name);
				}
				else
				{
					al.get(arg2).put("back", R.drawable.click);
					nameList.add(name);
				}
			}			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_friend, menu);
		return true;
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param)
	{
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue())
		{
		case GET_FRIEND_SUCCESS:
			bindAdapter();
			pd.cancel();
			break;
		case GET_FRIEND_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state)
			{
			case ResultCode.EMPTY:
				hint = "No friend";
				pd.cancel();
				Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

}
