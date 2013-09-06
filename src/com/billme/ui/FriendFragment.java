package com.billme.ui;


import java.util.ArrayList;
import java.util.HashMap;

import com.billme.logic.MainService;
import com.billme.widget.MyListViewAdapter;
import com.futurePayment.constant.Task;
import com.futurePayment.model.BankCard;
import com.futurePayment.model.Friend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class FriendFragment extends Fragment{
	
	private View view = null;
	private ListView list = null;
	private ProgressDialog pd = null;
	private MyListViewAdapter adapter = null;
	private ArrayList<HashMap<String, Object>> fl = new ArrayList<HashMap<String, Object>>();
	
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_friend, container, false);
		list = (ListView)view.findViewById(R.id.lv_fragment_friend);
		
		if (MainService.getFuturePayment().getUser().getFriendList().size() == 0) {
			if (this.pd == null) {
				this.pd = new ProgressDialog(this.getActivity());
			}
			pd.setMessage("Loading..");
			pd.show();

			Task task = new Task(Task.TASK_GET_BANK_CARD);
			MainService.newTask(task);
		} else {
			bindAdapter();
		}
		// TODO Auto-generated method stub
		return view;
	}	
	private void bindAdapter() {
		ArrayList<Friend> al = MainService.getFuturePayment().getUser()
				.getFriendList();
		for (int i = 0; i < al.size(); i++) {
			Friend f = al.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 加入图片
			map.put("icon", f.getPath());
			map.put("text", f.getName());
			fl.add(map);
		}
		adapter = new MyListViewAdapter(getActivity(), fl);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 选中某位好友
			}
		});
	}
}
